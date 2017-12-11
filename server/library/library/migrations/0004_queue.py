# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-12-11 03:09
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('library', '0003_auto_20171210_1256'),
    ]

    operations = [
        migrations.CreateModel(
            name='Queue',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('no', models.IntegerField()),
                ('QueSeat', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='library.Seat')),
                ('studentID', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='library.Student')),
            ],
        ),
    ]