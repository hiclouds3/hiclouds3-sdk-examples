

// deleteBucket
// delete all version objects
// perfrom multiple objects (at most 1000) delete
// if you want to delete more then 1000 objects you need to 
// modify the codes~

function deleteBucket (bucketName){
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




