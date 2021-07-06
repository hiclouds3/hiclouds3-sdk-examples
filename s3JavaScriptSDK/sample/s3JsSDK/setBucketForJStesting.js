
// Load the SDK 
var AWS = require('aws-sdk');

AWS.config.loadFromPath('./config.json');
var s3 = new AWS.S3();

settingBucketForTestingJSinBrowser('testjsbucket1');
listBucket();


function listBucket()
{
	s3.listBuckets(function(err, data) {
		if (err) console.log(err, err.stack); // an error occurred
		else     {
			console.log(data);
			console.log('we have '+data.Buckets.length.toString()+' buckets');
			for (var i = data.Buckets.length - 1; i >= 0; i--) {
				console.log(data.Buckets[i].Name);
			};
		}           // successful response
	});
}


function deleteBucket(bucketName)
{

	//testing deleteBucket
	var params = {
		Bucket: bucketName, /* required */
	};
	s3.listObjectVersions(params, function(err, data) {
		if (err) console.log(err, err.stack); // an error occurred
		else{
			console.log(data);
			params = {Bucket: bucketName};
			params.Delete = {};
			params.Delete.Objects = [];
			console.log(data.Versions.length);
			for(j = 0; j < data.Versions.length;++j)
			{
				params.Delete.Objects.push({Key: data.Versions[j].Key,
					VersionId: data.Versions[j].VersionId
				});
			}
			for(j = 0; j < data.DeleteMarkers.length;++j)
			{
				params.Delete.Objects.push({Key: data.DeleteMarkers[j].Key,
					VersionId: data.DeleteMarkers[j].VersionId
				});
			}
			s3.deleteObjects(params, function(err, data) {
				if (err) console.log(err, err.stack); // an error occurred
				else     {
					console.log(data);
				}
				params = {Bucket: bucketName};
				s3.deleteBucket(params, function(err, data) {
					if (err) console.log(err, err.stack); // an error occurred
					else     console.log(data);           // successful response
				});
			});

		}
	});

}








function settingBucketForTestingJSinBrowser (bucketName){
	var params = {
		Bucket: bucketName, /* required */
	};
	s3.createBucket(params, function(err, data) {
		if (err) console.log(err, err.stack); // an error occurred
		else     console.log(data);           // successful response
	}).on('complete',function(response)
		//put bucket cors for browsers javascript testing
	{
		var params = {
			Bucket: bucketName, /* required */
			CORSConfiguration: {
				CORSRules: [
					{
						AllowedHeaders: ['*',],
						AllowedMethods: ['GET','PUT','DELETE','POST'],
						AllowedOrigins: ['*','null'],
						ExposeHeaders:  ['ETag',],
					},
				]
			},
		};
		s3.putBucketCors(params, function(err, data) {
			if (err) console.log(err, err.stack); // an error occurred
			else     {
				console.log(data);
				console.log('put bucketcors succeed');
			}
		});
	}).send();
}
