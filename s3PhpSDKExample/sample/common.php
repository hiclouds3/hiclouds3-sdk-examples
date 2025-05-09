<?php
const SAMPLE_CONTENT = "abcdefghijklmnopqrstuvwxyz\n01234567890112345678901234\n!@#$%^&*()-=[]{};':',.<>/?\n01234567890112345678901234\nabcdefghijklmnopqrstuvwxyz\n";

function createSampleFile()
{
    $temp = tmpfile();
    $content = SAMPLE_CONTENT;
    fwrite($temp, $content);
    fseek($temp, 0);
    return $temp;
}

function showResult($result)
{
    if (!empty($result['Versions'])) {
        foreach ($result['Versions'] as $v) {
            echo "File Name: " . $v['Key'] . "\n";
            echo "VersionID: " . $v['VersionId'] . "\n";
            echo "File Size: " . $v['Size'] . "\n";
            echo "File ETag: " . $v['ETag'] . "\n";
            echo "-------------------------------------------------------------------------------\n";
        }
    }
}
