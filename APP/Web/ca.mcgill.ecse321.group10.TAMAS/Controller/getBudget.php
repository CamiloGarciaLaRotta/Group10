<?php
require_once __DIR__.'\CourseController.php';

$cc = new CourseController();

$CDN = $_POST['cdn'];
$budgetString = $cc->getBudget($CDN);
echo $budgetString;
?>