package com.example.fooddataviewer.scan

import com.example.fooddataviewer.MobiusVM
import com.example.fooddataviewer.scan.handlers.ProcessCameraFrameHandler
import com.spotify.mobius.Next
import com.spotify.mobius.Next.noChange
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import javax.inject.Inject

fun scanUpdate(
    model: ScanModel,
    event: ScanEvent
): Next<ScanModel, ScanEffect> {
    return when(event){
        is Captured -> Next.dispatch(setOf(ProcessCameraFrame(event.frame)))
        is Detected -> if(!model.activity){ //if for checking if api call is happening right now
            Next.next<ScanModel, ScanEffect>(model.copy(activity = true),
            setOf(ProcessBarcode(event.barcode)))
        }else{
            noChange<ScanModel, ScanEffect>()
        }
    }
}

class ScanViewModel @Inject constructor(
    processCameraFrameHandler: ProcessCameraFrameHandler
):
    MobiusVM<ScanModel, ScanEvent, ScanEffect>(
        "ScanViewModel",
        Update(::scanUpdate),
        ScanModel(activity = true),  // todo to inaczej niz u typeczka, robil to w 42/44 czesci chyba
        RxMobius.subtypeEffectHandler<ScanEffect, ScanEvent>()
            .addTransformer(ProcessCameraFrame::class.java, processCameraFrameHandler)
            .build()
    )