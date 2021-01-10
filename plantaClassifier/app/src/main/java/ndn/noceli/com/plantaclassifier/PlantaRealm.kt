package ndn.noceli.com.plantaclassifier

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PlantaRealm : RealmObject() {
    @PrimaryKey
    open var id:Int=0;
    open var codIdentificacao:Int=0
    open var nomeIdentificacao:String=""
    open var isnumero:Boolean=false
    open var caminho1:String=""
    open var codChave:Int=0

}