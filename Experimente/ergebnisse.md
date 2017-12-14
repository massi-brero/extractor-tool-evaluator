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

R2 --> wie 1
* [-m=3,-s=jaccard,-t=0.7,-w=5,-l=score]
* kompletter Textkorpus
* Volltexte
num_ret        	all	14077
num_rel        	all	7449
num_rel_ret    	all	4481
* Dauer: 1353


xx. kann hier evtl. auch irgendwo ein threshhold gesetzt werden?

#### MetaMap ####

14. Keine Parameter [--XMLf1,-R=NCBI]
* Volltexte
* ohne Disambiguiation
* Strict Data Model -> sollte schneller sein als der relaxed Modus (?)
num_ret        	all	14918
num_rel        	all	7449
num_rel_ret    	all	5602


15. mit Disambiguation Server [--XMLf1,-R=NCBI,-DISAMB]
* Volltexte
* mit Disambiguiation
num_ret        	all	15062
num_rel        	all	7449
num_rel_ret    	all	5645


16. mit Disambiguation Server [--XMLf1,-R=NCBI,-o,--DISAMB]
* Volltexte
* mit Disambiguiation
* allow Overmatches
--> ! Resource error: insufficient memory


17. Relaxed Model [--XMLf1,-R=NCBI,-C,--DISAMB]
* Volltexte
* Relaxed Model
* eigene DB installiert
* enthält mehr Strings u. a. mit Kunjunktionen (-> nochnal checken)
num_ret        	all	14883
num_rel        	all	7449
num_rel_ret    	all	5600


18. Relaxed Model [--XMLf1,-R=NCBI,-C,-z,--DISAMB]
* Volltexte
* Kombination von Relaxed Model und Term Processing (empfohlen)
* Term processing: Process terms, i.e., short text fragments, rather than a document containing complete sentences.
* eher für Listen geeignet oder Aufzeählungen / Stichwortsammlungen etc.
num_ret        	all	13324
num_rel        	all	7449
num_rel_ret    	all	4523


19. Relaxed Model [--XMLf1,-R=NCBI,-t,--DISAMB]
* Volltexte
* Bypass für part-of-speech-tagger
* Ergebnisse mit QuickUMLS vergleichbar ?? (Check!)
* geht ziemlich schnell
num_ret        	all	14918
num_rel        	all	7449
num_rel_ret    	all	5620


20. threshhold hochsetzen: [--XMLf1,-R=NCBI,-r=950,--DISAMB]
* Volltexte
* eleminiert false positives
num_ret        	all	2219
num_rel        	all	7449
num_rel_ret    	all	781


21. threshhold verringern: [--XMLf1,-R=NCBI,-r=800,--DISAMB]
* Volltexte
* eleminiert false positives
num_ret        	all	6016
num_rel        	all	7449
num_rel_ret    	all	2109


22. prefer multiple concepts: [--XMLf1,-R=NCBI,-Y,--DISAMB]
* Volltexte
* bewertet Mappings höher, die mehr Konzepte umfassen
* sinnvoll, um semantische Beziehungen zwischen den im Text gefundenen Konzept zu analysieren
num_ret        	all	11275
num_rel        	all	7449
num_rel_ret    	all	4057

23. composite phrases: [--XMLf1,-R=NCBI,-Q=0,--DISAMB]
* Volltexte
* keine Kompositionen von Phrasen mit Präpositionen generieren
* Tradeoff höhere Effizienz/geringere Qualität d. Ergebnisse
num_ret        	all	15107
num_rel        	all	7449
num_rel_ret    	all	5668

24. -no derivational variants: [--XMLf1,-R=NCBI,-d,--DISAMB]
* keine aus Derivation gebildeten Varianten
* vermeidet Änderungen im Sinn
* evtl. Verbesserung der Precision?
num_ret        	all	14674
num_rel        	all	7449
num_rel_ret    	all	5612

25. threshhold verringern: [--XMLf1,-R=NCBI,-r=700,--DISAMB]
* Volltexte
* eleminiert false positives
num_ret        	all	9411
num_rel        	all	7449
num_rel_ret    	all	3374


26. Untere Threshholdgrenze aussetzen und alle Ergebnisse anzeigen...: [--XMLf1,-R=NCBI] wie 14
* Volltexte
* erstaunlicherweise etwas mehr Relevante???
num_ret        	all	15114
num_rel        	all	7449
num_rel_ret    	all	5645


27. Untere Threshholdgrenze aussetzen und alle Ergebnisse anzeigen... r = 100;
* Volltexte
* wie 26
num_ret        	all	15114
num_rel        	all	7449
num_rel_ret    	all	5645

28. wie 15 aber mit Abstracts [--XMLf1,-R=NCBI,--DISAMB]
* Abstracts
* keine fehlerbehafteten Ergebnis XMLs!!
* Dauer: 272 !!!
* noch weniger als bei Quick-UMLS
* Zeichen, dass MM versucht Phrasen im ganzen Text zueinander in Beziehung zu setzen (???)

29. [--XMLf1,-R=NCBI,-o,--DISAMB]
* Abstracts
* bei Volltexten --> Memory Overflow

30. [--XMLf1,-R=NCBI,--blanklines=0,--DISAMB]

31. • Negation Detection ??





xx. -g (--allow concept gaps) ???

xx. -y (--word sense disambiguation) ??? 

xx. Text als eine Phrase behandeln









### Fazit ? ###
#### QuickUMLS ####
* Ergebnisse beruhen auf unterschiede zw. Trainingsset (und Absicht) und Goldstandard Texte
* im Grunde liegen die Werte aber doch nicht so weit weg von Soldainis eigenen Ergebnissen --> http://medir2016.imag.fr/data/slides_paper16.pdf
* längere Texte scheinen QuickUMLS Performance (??? mit MM vergleichen!!) und Qualität nicht wesentlich zu verändern
* Top Ergebnisse nur bei eigener Auswahl
* Optimierung der Ergebnisse (prakt. Anwendung für SenseCare) bedingt eigtl. ein auf den Artikeltypus abgestimmtes Feintuning der Ergebnisse
* ergebnisse aus den kurzen Texten werden auch bei den Volltexten bestätigt(s. 2. QuickUMLS Studie)

#### MetaMap ####
* Performance stark beeinflusst von Art/Umfang der Kandidatenbildung
* bzgl. Threshhold hilft nur Rantasten an denoptimalen Wert (wie wird r gebildet?)
* untere Grenze -500 (erwähnen!)


#### Generell  ####
* unbedingt erwähnen: Evaluation bei Abstracts nicht möglich -> Ausblick -> eigene Forschungsfrage (wie viel mehr KOnzepte werden gefunden wenn man die Volltexte scannt) -> auf Publikationen verweisen -> die dort aufgeworfenen Fragen sind jetzt mit SEE einfacher zu beantworten !!! -> evtl. kurz den Ansatz erklären (Positione ausmachen -> überflüssige KOnzepte aus den Resultdateien löschen in CRAFT ...)
* Evaluationen uns Performancemessung ohne Diskussion der verwendeten parameter sind eigtl. wertlos... dafür
beinflussen diese die Güte des Ergebnisses und die Verarbeitungsgeschwindigkeit einfach zu stark
* Benchmarking Software erlaubt Bezihungen zwischen Parametern und Tradeoffs aufzuzeigen:
 -> Beispiel Threshhold bei MM -> höherer Wert reduziert false positives, reduziert aber positive positives noch mehr


### To Do ###
* variieren: overlapping_criteria, similarity_name mit Standard Werten Sonst
* accepted semtypes verringern (Standard-Werte)? Einfluß auf Performance? ...Evaluation?




