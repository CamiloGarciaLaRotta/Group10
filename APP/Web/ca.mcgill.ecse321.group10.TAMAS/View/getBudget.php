<?php
require_once __DIR__.'\..\Controller\CourseController.php';

$cc = new CourseController();

$CDN = $_POST['cdn'];

echo $cc->getBudget($CDN);
?>