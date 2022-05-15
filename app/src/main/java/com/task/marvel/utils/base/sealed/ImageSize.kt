package com.task.marvel.utils.base.sealed

sealed class ImageSize {
    class Small(val name: String = "/portrait_small.") : UIEvent()
    class Medium(val name: String = "/portrait_medium.") : UIEvent()
    class XLarge(val name: String = "/portrait_xlarge.") : UIEvent()
    class UnCanny(val name: String = "/portrait_uncanny.") : UIEvent()
}
