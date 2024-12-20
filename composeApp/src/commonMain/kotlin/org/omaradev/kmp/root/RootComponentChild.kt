package org.omaradev.kmp.root

import org.omaradev.kmp.details.DetailsComponent
import org.omaradev.kmp.list.ListComponent

sealed class RootComponentChild {
    class ListChild(val component: ListComponent) : RootComponentChild()

    class DetailsChild(val component: DetailsComponent) : RootComponentChild()
}