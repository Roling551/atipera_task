### About
The program is created as a task for the first part of the recruiting process in Atipera company.\
API returns a list of repositories and their branches of user provided in the request.
### Set up
Before run it is advised to add line:
```
github.token=<github_api_token>
```
to the application.properties file. Otherwise number of uses of the API will be limited.
### Usage
API provides one endpoint:
```
localhost:8080/repositories/<github_user>
```
where <github_user> is a valid github user name.\
It is necessary to provide "Accept" ket of value "application/json".\
\
In response the API returns a list of user's repositories with list of their branches.
