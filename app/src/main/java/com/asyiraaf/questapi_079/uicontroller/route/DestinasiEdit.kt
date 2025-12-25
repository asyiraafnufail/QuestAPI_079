package com.asyiraaf.questapi_079.uicontroller.route

import com.asyiraaf.questapi_079.R

object DestinasiEdit : DestinasiNavigasi {
    override val route = "edit"
    override val titleRes = R.string.edit_siswa
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}