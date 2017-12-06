# Experimente Protokoll #

## Beobachtungen QuickUMLS lokal ##

### vollst. Corpus ###

Performance schwankt auf dem gleichen Systen (lokal) um fast 10% für gleiche Parameter

#### QuickUMLS ####

1: schlechte Werte bei Standardwerten
* [-m=3,-s=jaccard,-t=0.7,-w=5,-l=score]
* kompletter Textkorpus
* Volltexte
num_ret        	all	14077
num_rel        	all	7449
num_rel_ret    	all	4481


2: t Erhöhung verbessert Precision aber kaum den Recall
* {-m=3, -s=jaccard, -t=1.0, -w=5, -l=score}
* kompletter Textkorpus
* Volltexte
* hohe Performance
num_ret        	all	9288
num_rel        	all	7449
num_rel_ret    	all	4371

3: minimal Length +1
* [-m=4,-s=jaccard,-t=0.7,-w=5,-l=score}
* kompletter Textkorpus
* Volltexte
num_ret        	all	13919
num_rel        	all	7449
num_rel_ret    	all	4419
* Ergebnis sehr ähnlich zu Run 1

4: wie Run3 aber t auf 1
* [-m=4,-s=jaccard,-t=1.0,-w=5,-l=score]
* kompletter Textkorpus
* Volltexte
* hohe Performance
num_ret        	all	9130
num_rel        	all	7449
num_rel_ret    	all	4309


5: wie 4 aber t kleiner auf 0.8
* {-m=4, -s=jaccard, -t=0.8, -w=5, -l=score}
* kompletter Textkorpus
* Volltexte
num_ret        	all	9996
num_rel        	all	7449
num_rel_ret    	all	4371

6: m auf 5 und t etwas runter (0.7)
* {-m=5, -s=jaccard, -t=0.7, -w=5, -l=score}
* kompletter Textkorpus
* Volltexte
* sehr schlechter Recall + Precision
num_ret        	all	7789
num_rel        	all	7449
num_rel_ret    	all	1434


7: sehr niedrige minLength
* [-m=2,-s=jaccard,-t=0.7,-w=5,-l=score]
* kompletter Textkorpus
* Volltexte
num_ret        	all	14089
num_rel        	all	7449
num_rel_ret    	all	4481


8: Standardwerte aber similarity_name = cosine
* kompletter Textkorpus
* Volltexte
* [-w=5,-t=0.7,-m=3,-s=cosine,-l=score]
num_ret        	all	103013
num_rel        	all	7449
num_rel_ret    	all	4882

9: Standardwerte aber similarity_name = overlap
* [-w=5,-t=0.7,-m=3,-s=overlap,-l=score]
* nur für 11597317.txt  
* Volltext
* für > 3 Mio. Konzepte -> Linux Fehler beim Versuch die datei zu schreiben
* dauert extrem lang
* es werden sehr viele Konmzepte gefunden
num_ret        	all	314252
num_rel        	all	12
num_rel_ret    	all	11


10: Standardwerte aber overlapping_criteria = length
* [-w=5,-t=0.7,-m=3,-s=jaccard,-l=length]
num_ret        	all	14077
num_rel        	all	7449
num_rel_ret    	all	4481

11.Nur Abstracts - Parameter wie 1.
* [-m=3,-s=jaccard,-t=0.7,-w=5,-l=score]
* Abstracts
* kompletter Korpus

12.Nur Abstracts - Parameter wie 3.
* [-m=4,-s=jaccard,-t=1.0,-w=5,-l=score]
* Abstracts
* kompletter Korpus

13.Nur Abstracts - Parameter wie 7.
* [-m=2,-s=jaccard,-t=0.7,-w=5,-l=score]
* Abstracts
* kompletter Korpus

#### MetaMap ####

14. Keine Parameter [--XMLf1,-R=NCBI]
* Volltexte
* ohne Disambiguiation
* Strict Data Model -> sollte schneller sein als der relaxed Modus (?)

15. mit Disambiguation Server [--XMLf1,-R=NCBI,-DISAMB]
* Volltexte
* mit Disambiguiation


15. mit Disambiguation Server [--XMLf1,-R=NCBI,-C,-DISAMB]
* Volltexte
* mit Disambiguiation
* Relaxed Model

16. Relaxed Model [--XMLf1,-R=NCBI,-C]
* Volltexte
* Kombination von Relaxed Model
*

17. Relaxed Model [--XMLf1,-R=NCBI,-C,-]
* Volltexte
* Kombination von Relaxed Model und Term Processing (empfohlen)
* Term processing: Jeder Input wird wie eine zusammengehörende Phrase behandelt, um
auch komplexere KOnzepte zu erkennen
* (brauchbar?)




### Fazit ? ###
#### QuickUMLS ####
* Ergebnisse beruhen auf unterschiede zw. Trainingsset (und Absicht) und Goldstandard Texte
* im Grunde liegen die Werte aber doch nicht so weit weg von Soldainis eigenen Ergebnissen --> http://medir2016.imag.fr/data/slides_paper16.pdf
* längere Texte scheinen QuickUMLS Performance (??? mit MM vergleichen!!) und Qualität nicht wesentlich zu verändern
* Top Ergebnisse nur bei eigener Auswahl
* Optimierung der Ergebnisse (prakt. Anwendung für SenseCare) bedingt eigtl. ein auf den Artikeltypus abgestimmtes Feintuning der Ergebnisse
* ergebnisse aus den kurzen Texten werden auch bei den Volltexten bestätigt(s. 2. QuickUMLS Studie)

#### MetaMap ####


#### Generell  ####
* Evaluationen uns Performancemessung ohne Diskussion der verwendeten parameter sind eigtl. wertlos... dafür
beinflussen diese die Güte des Ergebnisses und die Verarbeitungsgeschwindigkeit einfach zu stark

### To Do ###
* variieren: overlapping_criteria, similarity_name mit Standard Werten Sonst
* accepted semtypes verringern (Standard-Werte)? Einfluß auf Performance? ...Evaluation?




