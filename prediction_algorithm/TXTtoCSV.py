import csv

txt_file = 'Intel/Intel1.txt'
csv_file = 'Intel/Intel1.csv'

in_txt = csv.reader(open(txt_file, "rb"), delimiter = '\t')
out_csv = csv.writer(open(csv_file, 'wb'))

out_csv.writerows(in_txt)
