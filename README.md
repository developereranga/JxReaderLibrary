# JxReaderLibrary
## Importing library to your project
 1. Download the or clone a copy to your computer.
 2. In android studio 
   ```ruby File -> New -> Import Module -> Select libjxt folder in cloned dir

## What is this 
1. This is a networking library
   1. Example:  when client application send a request to server over an HTTP request, the back  end could be handled by this library.
2. Mobile app designers getting better and  better designs,  which puts engineers in challenge.  
3. Most current designs are vary beautiful  with lot of metadata. These things required tons of network requests. 
4. JxReader makes  it  very easy for  the users to send a request without worrying about threading  or synchronization  in the client side. 
 
  
## JxReader Implementation 
### Parameters  
####JxReader  required 
1. Type  of request (JxReader  supports POST,GET,JsonPost[WCF])   
2. Server  URL 
3. Json Objec (if  not pass null)
4. POST  or GET parameter (Name Value pairs) 

```ruby
JxReader jxReader = new JxReader(type of request,url,jsonObject, params);
```

### JxReader JsonPost Request

```ruby
JSONObject tempObj = new JSONObject();
try {
tempObj.put("name", "Eranga");
} catch (Exception e) {
}
JxReader jxReader = new JxReader(new JxReader().JSON_POST, "http://droidblaster.net/jxreader/jsonPost.php", tempObj, null);
jxReader.setListener(new JxReader.JxReaderListener() {
@Override    public void onHttpRequesting() {
progressDialog.show();
}
@Override
public void onResponseRecived(JsonOut result) {
tResponse.setText(result.getResponse().toString());
progressDialog.dismiss();
}
});
jxReader.execute();
```

### JxReader GET Request 

```ruby
List<NameValuePair> params = new ArrayList<NameValuePair>();
params.add(new BasicNameValuePair("name", "Eranga"));
JxReader jxReader = new JxReader(new JxReader().GET, "http://droidblaster.net/jxreader/get.php", null, params);
jxReader.setListener(new JxReader.JxReaderListener() {
@Override
public void onHttpRequesting() {
progressDialog.show();
}
@Override
public void onResponseRecived(JsonOut result) {
tResponse.setText(result.getResponse().toString());
progressDialog.dismiss();
}
});
jxReader.execute();
```

### JxReader POST Request 

```ruby

List<NameValuePair> params = new ArrayList<NameValuePair>();
params.add(new BasicNameValuePair("name", "Eranga"));
JxReader jxReader = new JxReader(new JxReader().POST, "http://droidblaster.net/jxreader/post.php", null, params);
jxReader.setListener(new JxReader.JxReaderListener() {
@Override
public void onHttpRequesting() {
progressDialog.show();
}
@Override
public void onResponseRecived(JsonOut result) {
tResponse.setText(result.getResponse().toString());
progressDialog.dismiss();
}
});
jxReader.execute();

```

## JxReader request tracing system

*. We can get final output as an an instance of JsonOut. This class provide most critical information to developer. 
If an error occurred, developer can get to know about it by calling the method hasError()

```ruby
if(!jsonOut.hasError())
```

When an error is occurred, developer can get error message as a response, we can  show that error by invoking the method

```ruby
JsonObject obj=jsonOut.getResponse();
Toast.makeText(context,obj.getString(jsonOut.ERROR),Toast.LENGTH_LONG).show();
```

If there are no any error JsonOut response will provide all the data. 

# JxReader extra features
##Image Loader(Sample)

```ruby
 final ImageLoader loader = new ImageLoader(context);
loader.LoadImage("url", imageView, defaultImageResource);
```



