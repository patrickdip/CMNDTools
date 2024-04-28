package cmndtools.dipumba.com.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoteDovecListing(
    val map: String,
    //val name: String,
    //val tabs: TabsUnion,
    val type: String,
    //val cover: String,
    val price: String,
    //val thumb: String,
    val title: String,
    //val slider: Slider,
    //val content: String,
    val external: Item,
    val features: FeatureItem,
    val interior: Item,
    val location: String,
    val pricetype: String,
    val shorttext: String,
    //val description: String
){
    fun toDovecListing(): DovecListing{
        return DovecListing(
            map = map,
            type = type,
            price = price,
            title = title,
            externalFacilities = external.item,
            features = features.item,
            interiorFacilities = interior.item,
            location = location,
            pricetype = pricetype,
            shorttext = shorttext
        )
    }
}

@Serializable
data class Feature(
    val name: String,
    val statu: String
)

@Serializable
data class FeatureItem(
    val item: Array<Feature>
)

@Serializable
data class Item(
    val item: Array<String>
)

@Serializable
data class DovecListing(
    val map: String,
    val type: String,
    val price: String,
    val title: String,
    val externalFacilities: Array<String>,
    val features: Array<Feature>,
    val interiorFacilities: Array<String>,
    val location: String,
    val pricetype: String,
    val shorttext: String,
    var status: String = "AVAILABLE",
    var customerFeedback: FeedbackData? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DovecListing

        if (map != other.map) return false
        if (type != other.type) return false
        if (price != other.price) return false
        if (title != other.title) return false
        if (!externalFacilities.contentEquals(other.externalFacilities)) return false
        if (!features.contentEquals(other.features)) return false
        if (!interiorFacilities.contentEquals(other.interiorFacilities)) return false
        if (location != other.location) return false
        if (pricetype != other.pricetype) return false
        if (shorttext != other.shorttext) return false

        return true
    }

    override fun hashCode(): Int {
        var result = map.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + externalFacilities.contentHashCode()
        result = 31 * result + features.contentHashCode()
        result = 31 * result + interiorFacilities.contentHashCode()
        result = 31 * result + location.hashCode()
        result = 31 * result + pricetype.hashCode()
        result = 31 * result + shorttext.hashCode()
        return result
    }
}