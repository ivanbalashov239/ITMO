#! /usr/bin/gnuplot -persist

#set size ratio 0.71
set terminal pdfcairo enhanced font "Arial,20" size 29.7cm, 21.0cm
set output "graph1.pdf"
#set encoding utf8 
set title "График зависимости периода от расстояние между экраном и объектом ({/Symbol D}x(L)) для расстояния между щелями 0.85мм"
set xlabel "L, см"
set ylabel "{/Symbol D}x, см"
set grid xtics ytics mxtics mytics
set mxtics 5
set mytics 0.01
set nokey

f(x)=a*x+b
fit f(x) 'dataSet1' using 1:2 via a,b 
plot 	f(x),\
		'dataSet1' using 1:2 with points ps 0.5 lt 7