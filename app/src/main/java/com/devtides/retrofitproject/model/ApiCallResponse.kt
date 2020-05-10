package com.devtides.retrofitproject.model

data class ApiCallResponse(
    val method : String?,
    val query : Map<String, String>?,
    val headers : Map<String, String>?
) {
    fun flatten() : List<Item> {
        var flatpack = arrayListOf<Item>()
        method?.let { flatpack.add(Item("method", method, TYPE_ITEM)) }
        query?.let {
            if(!query.values.isEmpty()) {
                flatpack.add(Item("query", "", TYPE_CATEGORY))
                addMapItems(query, flatpack)
            }
        }

        headers?.let {
            if(!headers.values.isEmpty()) {
                flatpack.add(Item("headers", "", TYPE_CATEGORY))
                addMapItems(headers, flatpack)
            }
        }

        return flatpack
    }

    private fun addMapItems(map : Map<String, String>, flatpack : ArrayList<Item>) {
        for(key in map.keys) {
            flatpack.add(Item(key, map.getValue(key), TYPE_ITEM))
        }
    }
}