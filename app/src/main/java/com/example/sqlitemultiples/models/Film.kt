package com.example.sqlitemultiples.models

class Film {
    var id: Int? = null
    var name: String? = null
    var yili: String? = null
    var category: Category? = null
    constructor()
    constructor(id: Int?, name: String?, yili: String?, category: Category?) {
        this.id = id
        this.name = name
        this.yili = yili
        this.category = category
    }

    constructor(name: String?, yili: String?, category: Category?) {
        this.name = name
        this.yili = yili
        this.category = category
    }


}