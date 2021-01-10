package ndn.noceli.com.plantaclassifier

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ChaveRealm : RealmObject() {
    @PrimaryKey
    open var codChave:Int=0
    open var nomeChave:String=""

}