import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;


public class mainMongoTest {
    public static void main(String[] args) {

        //Establecemos conexion con el servidor MongoDB
        MongoClient client = MongoClients.create("mongodb://127.0.0.1:27017/");
        //Instanciamos base de datos
        MongoDatabase database = client.getDatabase("Biblioteca_manga");
        //Instanciamos coleccion o tabla
        MongoCollection collection = database.getCollection("Manga");
        System.out.println("Conexion establecida");
        //Creamos un objeto query para la consulta
        BasicDBObject query = new BasicDBObject();
        query.put("autor", "Eiichiro Oda");
        //Creamos un cursor el cual utilice el objeto anterior para iterar en una busqueda e imprimir los resultados
        MongoCursor cursor = collection.find(query).iterator();
        System.out.println("Los resultados de la consulta son:");
        while (cursor.hasNext()){
            System.out.println(cursor.next());
        }
        //Instanciamos un par de documentos para la busqueda y seleccion del que queremos actualizar
        Document documentoAuxiliar = new Document("titulo", "One Piece");
        Document documentoBusqueda = (Document) collection.find(documentoAuxiliar).first();
        //En caso de existir dicho documento, actualizamos un valor de un campo determinado mediante el uso de objetos Bson
        if(documentoBusqueda!=null) {
            Bson updateValor = new Document("numero_volumenes", 202);
            Bson updateOperacion = new Document("$set", updateValor);
            collection.updateOne(documentoBusqueda, updateOperacion);
            System.out.println("Valores actualizados correctamente");
        }
        client.close();
        System.out.println("Conexion cerrada");
    }
}
