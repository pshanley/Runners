Build Instructions
* Dev - just run the main method
    * Will use local mysql and phpmyadmin
    * localhost/phpmyadmin
* Staging/Prod 
    * Deploy as WAR using Run Configurations build, or "package" with the prod profile checked
    * Docker-compose up --build -d
    * Docker-compose down --rmi local 
    * phpmyadmin is localhost:8081
