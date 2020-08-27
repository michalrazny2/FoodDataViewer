package com.example.fooddataviewer.utils

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.runner.Description

// this class will help us check if the recycler view from foodlist works fine
class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            var resources: Resources? = null
            var childView: View? = null

            override fun describeTo(description: org.hamcrest.Description?) {
                var idDescription = Integer.toString(recyclerViewId)
                if (this.resources != null) {
                    idDescription = try {
                        this.resources!!.getResourceName(recyclerViewId)
                    } catch (var4: Resources.NotFoundException) {
                        String.format(
                            "%s (resource name not found)",
                            Integer.valueOf(recyclerViewId)
                        )
                    }
                }

                description?.appendText("with id: $idDescription")
            }

            public override fun matchesSafely(view: View): Boolean {

                this.resources = view.resources

                if (childView == null) {
                    val recyclerView =
                        view.rootView.findViewById<View>(recyclerViewId) as RecyclerView
                    if (recyclerView.id == recyclerViewId) {
                        childView =
                            recyclerView.findViewHolderForAdapterPosition(position)!!.itemView
                    } else {
                        return false
                    }
                }

                return if (targetViewId == View.NO_ID) {
                    view === childView
                } else {
                    val targetView = childView!!.findViewById<View>(targetViewId)
                    view === targetView
                }
            }
        }
    }
}

fun withRecyclerView(recyclerId: Int): RecyclerViewMatcher {
    return RecyclerViewMatcher(recyclerId)
}