package com.nfd.libgenscan

import android.net.Uri

object LibgenUri {
    fun getIsbnUri(id: String): Uri = Uri.parse(
            "http://libgen.is/search.php?req=" + id +
                    "&lg_topic=libgen&open=0&view=simple&res=25&phrase=1&column=identifier")

    fun getTitleUri(title: String): Uri = Uri.parse(
            "http://libgen.is/search.php?req=" + title +
                    "&open=0&res=25&view=simple&phrase=1&column=title")
}