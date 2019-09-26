# urlshortener

Simple app that lets you shorten a url by using redirects

## How to run

First, maven needs to be installed, then in the project folder
```
mvn install
```
Start the application with

```
mvn spring-boot:run
```

## Usage

Send a POST request with the url as the message body. The server should respond with:
```
{ "longUrl": "yourlongencodedurl", "shortUrl": "yournewshorturl" }
```
*yournewshorturl* will be in the form:
```
http://domain.com?id=abc
```
Where the id is generated sequentially, using digits, lowercase and uppercase letters.

## Limitations

* Not checking whether a url is actually valid or not
* Using a stub database class with a Map to store the id-url pairs, so data is not persistent and the number of stored urls is limited by memory size
* Relying on external error handling when sending bad requests
* Because the host is retrieved from the request header, this can be spoofed
* Not limiting the number of requests that can be sent from a given ip
