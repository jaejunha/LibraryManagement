# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-12-12 02:28
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('library', '0004_queue'),
    ]

    operations = [
        migrations.CreateModel(
            name='TimeLine',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('no', models.IntegerField()),
                ('content', models.CharField(max_length=100)),
            ],
        ),
    ]