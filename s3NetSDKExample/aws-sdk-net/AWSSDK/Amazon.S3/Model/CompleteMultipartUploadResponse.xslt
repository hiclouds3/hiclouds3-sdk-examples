<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:s3="http://s3.amazonaws.com/doc/2006-03-01/" exclude-result-prefixes="xsl s3">
  <xsl:output method="xml" omit-xml-declaration="no" indent="yes"/>
  <xsl:variable name="ns" select="'http://s3.amazonaws.com/doc/2006-03-01/'"/>

  <xsl:template match="s3:CompleteMultipartUploadResult">
    <CompleteMultipartUploadResponse>
      <BucketName>
        <xsl:value-of select="s3:Bucket"/>
      </BucketName>
      <Key>
        <xsl:value-of select="s3:Key"/>
      </Key>
      <Location>
        <xsl:value-of select="s3:Location"/>
      </Location>
      <ETag>
        <xsl:value-of select="s3:ETag"/>
      </ETag>
  </CompleteMultipartUploadResponse>
  </xsl:template>
</xsl:stylesheet>
