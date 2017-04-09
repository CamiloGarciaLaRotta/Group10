<?php
require_once __DIR__.'\CourseController.php';

// getter script for budget information

$cc = new CourseController();

echo $cc->getRemainingBudget($_POST['cdn']);
?>