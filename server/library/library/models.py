# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from django.db import models
import datetime

class Library(models.Model):
    zone = models.CharField(max_length=5)
    open = models.IntegerField()
    def __str__(self):
        return self.zone

class Student(models.Model):
    id = models.IntegerField(primary_key=True)
    def __str__(self):
        return self.id

class Seat(models.Model):
    no = models.IntegerField()
    libraryZone = models.ForeignKey(Library, on_delete=models.CASCADE)
    use = models.IntegerField()
    studentID = models.ForeignKey(Student, on_delete=models.CASCADE)
    time = models.DateTimeField(default=datetime.datetime.now)
    class Meta:
        unique_together = (('no', 'libraryZone'),)

