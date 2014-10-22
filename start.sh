java -jar order-context-parent/order-application/target/order-application-1.0-SNAPSHOT.jar server order-context-parent/order-application/dev.yml &

java -jar product-catalog-context/target/product-catalog-context-1.0-SNAPSHOT.jar server product-catalog-context/dev.yml &

java -jar shopping-context/target/shopping-context-1.0-SNAPSHOT.jar server shopping-context/dev.yml &

mvn -pl product-catalog-context exec:java -Dexec.mainClass="se.citerus.cqrs.bookstore.productcatalog.application.TestProductDataImporter" -Dexec.classpathScope="test"

mvn -pl order-context-parent/order-application exec:java -Dexec.mainClass="se.citerus.cqrs.bookstore.ordercontext.application.TestContractDataImporter" -Dexec.classpathScope="test"


