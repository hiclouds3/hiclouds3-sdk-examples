



function getObject(s3, bucketName, key)
{
	var deferred = Q.defer();
	var params = {
	Bucket: bucketName,
	Key: key,
	}
	s3.getObject(params, function(err, data){
		if (err) 
			console.log(err,err.stack);
		else 
		{
			console.log(data.Body.toString());
			deferred.resolve(data);
		}
		});
	return deferred.promise;
}


function listObject(s3, bucketName)
{
	var deferred = Q.defer();
	//listing object's key
	var dataContents = [];// content array
	var params = {
	  Bucket: bucketName, /* required */
	  // Prefix: 'STRING_VALUE'
	};
	s3.listObjects(params, function(err, data) {
	  if (err) console.log(err, err.stack); // an error occurred
	  else     {
	  	dataContents = data.Contents;
	  	console.log(data.Contents);
	  	for(index = 0; index < data.Contents.length;index++)
	  		console.log(data.Contents[index].Key.toString()); 
	  	deferred.resolve(data);
	  }
	});
	return deferred.promise;
}

//clean all object but version objects
function deleteAllObjects(s3, bucketName)
{
	var deferred = Q.defer();
	console.log("clean all objects");
	var params = {
	  Bucket: bucketName, /* required */
	  // Prefix: 'STRING_VALUE'
	};
	s3.listObjects(params, function(err, data) {
	  if (err) console.log(err, err.stack); // an error occurred
	  else     {
	  	dataContents = data.Contents;
	  	// console.log(data.Contents);
	  	for(index = 0; index < data.Contents.length;index++)
	  	{
	  		console.log('delete object '+data.Contents[index].Key.toString()); 
	  		deleteObject(s3, bucketName, data.Contents[index].Key.toString());
	  	}
	  	deferred.resolve(data);
	  }
	});
	return deferred.promise;
}

function deleteObject(s3, bucketName, key)
{
	var deferred = Q.defer();
	var params = {
	  Bucket: bucketName, /* required */
	  Delete: { /* required */
	    Objects: [ /* required */
	      {
	        Key: key, /* required */
	      },
	      /* more items */
	    ],
  	  },
	};
	s3.deleteObjects(params, function(err, data) {
	  if (err) console.log(err, err.stack); // an error occurred
	  else{
		  console.log(data);
	  	  deferred.resolve(data);
	  }
	});
	return deferred.promise;
}

function putObject(s3, bucketName, key, upload_data)
{
	var deferred = Q.defer();
	var params = {
	Bucket: bucketName,
	Key: key,
	ACL: 'public-read',
	Body: upload_data,
	ContentType :'text',
	}
	s3.putObject(params, function(err,data){
		if (err) console.log(err, err.stack);
		else {
			console.log(data);
		  	deferred.resolve(data);
		}
	});
	return deferred.promise;
}



	// function getObject(s3, bucketName, key)
	// {
	// 	var params = {
	// 	Bucket: bucketName,
	// 	Key: key,
	// 	}
	// 	s3.getObject(params, function(err, data){
	// 		if (err) 
	// 			console.log(err,err.stack);
	// 		else 
	// 			console.log(data.Body.toString());
	// 		});
	// }


	// function listObject(s3, bucketName)
	// {
	// 	//listing object's key
	// 	var dataContents = [];// content array
	// 	var params = {
	// 	  Bucket: bucketName, /* required */
	// 	  // Prefix: 'STRING_VALUE'
	// 	};
	// 	s3.listObjects(params, function(err, data) {
	// 	  if (err) console.log(err, err.stack); // an error occurred
	// 	  else     {
	// 	  	dataContents = data.Contents;
	// 	  	console.log(data.Contents);
	// 	  	for(index = 0; index < data.Contents.length;index++)
	// 	  		console.log(data.Contents[index].Key.toString()); 
	// 	  }
	// 	});
	// }


	// function deleteAllObjects(s3, bucketName)
	// {
	// 	console.log("clean all objects");
	// 	var params = {
	// 	  Bucket: bucketName, /* required */
	// 	  // Prefix: 'STRING_VALUE'
	// 	};
	// 	s3.listObjects(params, function(err, data) {
	// 	  if (err) console.log(err, err.stack); // an error occurred
	// 	  else     {
	// 	  	dataContents = data.Contents;
	// 	  	// console.log(data.Contents);
	// 	  	for(index = 0; index < data.Contents.length;index++)
	// 	  	{
	// 	  		console.log('delete object '+data.Contents[index].Key.toString()); 
	// 	  		deleteObject(s3, bucketName, data.Contents[index].Key.toString());
	// 	  	}
	// 	  }
	// 	});
	// }

	// function deleteObject(s3, bucketName, key)
	// {
	// 	var params = {
	// 	  Bucket: bucketName, /* required */
	// 	  Delete: { /* required */
	// 	    Objects: [ /* required */
	// 	      {
	// 	        Key: key, /* required */
	// 	      },
	// 	      /* more items */
	// 	    ],
	//   	  },
	// 	};
	// 	s3.deleteObjects(params, function(err, data) {
	// 	  if (err) console.log(err, err.stack); // an error occurred
	// 	  else     console.log(data);           // successful response
	// 	});
	// }

	// function putObject(s3, bucketName, key, upload_data)
	// {
	// 	var params = {
	// 	Bucket: bucketName,
	// 	Key: key,
	// 	ACL: 'public-read',
	// 	Body: upload_data,
	// 	ContentType :'text',
	// 	}
	// 	s3.putObject(params, function(err,data){
	// 		if (err) console.log(err, err.stack);
	// 		else console.log(data);	
	// 	});
	// }

	// function executeAsynchronously(functions, timeout) {
	//   for(var i = 0; i < functions.length; i++) {
	//     setTimeout(function(){functions[i]}, timeout);
	//   }
	// }