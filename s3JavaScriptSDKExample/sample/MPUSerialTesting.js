var fileChooser = document.getElementById('file-chooser');
var button = document.getElementById('upload-button');
var results = document.getElementById('results');
var tags = new Array(3);
var uploadId = '';

// line: 92
// urlToImg () will take some time to download img. 

button.addEventListener('click', function() {
  console.log('have '+ fileChooser.files.length+ ' files');
    //initiate multipart
    var params = {
    Key: 'objectKey', /* required */
    Bucket: testBucketName,
    ACL: 'public-read'
    };
    s3.createMultipartUpload(params, function(err, data) {
      if (err) console.log(err, err.stack); // an error occurred
      else     {

        console.log(data);
        uploadId = data.UploadId;
        
        var file = fileChooser.files[0];
        var file2 = fileChooser.files[1];
        var file3 = fileChooser.files[2];

        // console.log(file);
        if (file&&file2&&file3) {
          console.log(file.name);

          var params = {Bucket: testBucketName, Key: 'objectKey',
          Body: file, PartNumber: 1, UploadId: uploadId};
          
          s3.uploadPart(params, function (err, data) {
              // results.innerHTML = err ? 'ERROR!' : 'UPLOADED.';
              if (err) console.log(err, err.stack); // an error occurred
              else     {
                tags[0] = data.ETag;
                console.log(tags[0]);
                //upload second
                var params = {Bucket: testBucketName, Key: 'objectKey',
                Body: file2, PartNumber: 2, UploadId: uploadId};
                
                s3.uploadPart(params, function (err, data) {
                    // results.innerHTML = err ? 'ERROR!' : 'UPLOADED.';
                    if (err) console.log(err, err.stack); // an error occurred
                    else     {
                      tags[1] = data.ETag;
                      console.log(tags[1]);
                      //upload third
                      var params = {Bucket: testBucketName, Key: 'objectKey',
                      Body: file3, PartNumber: 3, UploadId: uploadId};
                      
                      s3.uploadPart(params, function (err, data) {
                          // results.innerHTML = err ? 'ERROR!' : 'UPLOADED.';
                          if (err) console.log(err, err.stack); // an error occurred
                          else     {
                            tags[2] = data.ETag;
                            //complete all parts
                            console.log(tags[0]);
                            console.log(tags[1]);
                            console.log(tags[2]);
                            console.log(uploadId);
                            var params = {
                              Bucket: testBucketName, /* required */
                              Key: 'objectKey', /* required */
                              UploadId: uploadId, /* required */
                              MultipartUpload: {
                                Parts: [
                                  {
                                    ETag: tags[0],
                                    PartNumber: 1
                                  },
                                  {
                                    ETag: tags[1],
                                    PartNumber: 2
                                  },
                                  {
                                    ETag: tags[2],
                                    PartNumber: 3
                                  },
                                  /* more items */
                                ]
                              },
                            };
                            s3.completeMultipartUpload(params, function(err, data) {
                              results.innerHTML = err ? 'ERROR!' : 'SAVED.';
                              if (err) console.log(err, err.stack); // an error occurred
                              else     {
                                console.log(data);
                                urlToImg();//use url to save file from s3
                              }           // successful response
                            })

                          }
                      });           
                    }
                });
           
              }
          });

        } else {
          results.innerHTML = 'Nothing to upload.';
        }
        
      }
    });
}, false);  


function urlToImg (){
  var params = {Bucket: testBucketName, Key: 'objectKey'};
  s3.getSignedUrl('getObject', params, function (err, url) {
    console.log("The URL is", url);
      saveFileToimg(url);
  });
}


function saveFileToimg(url) {
  // Get file name from url.
  var filename = url.substring(url.lastIndexOf("/") + 1).split("?")[0]+'.jpeg';
  var xhr = new XMLHttpRequest();
  xhr.responseType = 'blob';
  xhr.onload = function() {
    var a = document.createElement('a');
    a.href = window.URL.createObjectURL(xhr.response); // xhr.response is a blob
    a.download = filename; // Set the file name.
    a.style.display = 'none';
    document.body.appendChild(a);
    a.click();
    delete a;
  };
  xhr.open('GET', url);
  xhr.send();
}