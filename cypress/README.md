## Cypress Service
* This cypress test logs into Instagram and grabs the session and csrf cookies and writes them to a log file
* The cookies are mounted into tomcat where they can be used to make instagram requests
* [A docker client](https://github.com/docker-java/docker-java) in tomcat starts the cypress container which runs the test

#### Updating Cypress Images
* docker build -t pshanley323/instagram-cypress .
    * tags as `latest` by default
* docker push pshanley323/instagram-cypress


##### Testing Locally
* docker build -t pshanley323/instagram-cypress .
* docker run --ipc=host -v ...AbsolutePathTo.../Runners/cypress/cookies:/app/cypress/cookies -t pshanley323/instagram-cypress .
* Docker won't mount the `/usr/local/runners/cookies` locally (even when making the folder permissions non-root owned) but it works on the ec2 instance
