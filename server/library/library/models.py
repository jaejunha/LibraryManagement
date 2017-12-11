# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from django.db import models

class Area(models.Model):
    zone = models.CharField(max_length=5)
    open = models.IntegerField()
    def __str__(self):
        return self.zone

class Student(models.Model):
    id = models.IntegerField(primary_key=True)
    status = models.CharField(max_length=10, default='no use')
    def __str__(self):
        return self.id

class Seat(models.Model):
    no = models.IntegerField()
    AreaZone = models.ForeignKey(Area, on_delete=models.CASCADE)
    use = models.IntegerField()
    studentID = models.ForeignKey(Student, null=True)
    time = models.IntegerField(default=900)

class Queue(models.Model):
    no = models.IntegerField()
    QueSeat = models.ForeignKey(Seat, on_delete=models.CASCADE)
    studentID = models.ForeignKey(Student, null=True)