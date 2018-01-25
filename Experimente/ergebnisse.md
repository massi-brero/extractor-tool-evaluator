# Experimente Protokoll #

## Beobachtungen QuickUMLS lokal ##

### CRAFT ###
* 731 Einträge in der Datenbank!

Performance schwankt auf dem gleichen Systen (lokal) um fast 10% für gleiche Parameter

#### QuickUMLS ####

1: schlechte Werte bei Standardwerten [-m=3,-s=jaccard,-t=0.7,-w=5,-l=score]
* kompletter Textkorpus
* Volltexte
num_ret        	all	14077
num_rel        	all	7437
num_rel_ret    	all	4481

2: t Erhöhung verbessert Precision aber kaum den Recall
* [-m=3,-s=jaccard,-t=1.0,-w=5,-l=score]
* kompletter Textkorpus
* Volltexte
* hohe Performance
num_ret        	all	9288
num_rel        	all	7437
num_rel_ret    	all	4371

3: minimal Length +1
* [-m=4,-s=jaccard,-t=0.7,-w=5,-l=score]
* kompletter Textkorpus
* Volltexte
num_ret        	all	13919
num_rel        	all	7437
num_rel_ret    	all	4419
* Ergebnis sehr ähnlich zu Run 1

4: wie Run3 aber t auf 1
* [-m=4,-s=jaccard,-t=1.0,-w=5,-l=score]
* kompletter Textkorpus
* Volltexte
* hohe Performance
num_ret        	all	9130
num_rel        	all	7437
num_rel_ret    	all	4309

5: wie 4 aber t kleiner auf 0.8
* 	
* kompletter Textkorpus
* Volltexte
num_ret        	all	9996
num_rel        	all	7437
num_rel_ret    	all	4371

6: m auf 5 und t etwas runter (0.7)
* [-m=5,-s=jaccard,-t=0.7,-w=5,-l=score]
* kompletter Textkorpus
* Volltexte
* sehr schlechter Recall + Precision
num_ret        	all	7789
num_rel        	all	7437
num_rel_ret    	all	1434

7: sehr niedrige minLength
* [-m=2,-s=jaccard,-t=0.7,-w=5,-l=score]
* kompletter Textkorpus
* Volltexte
um_ret        	all	14089
num_rel        	all	7437
num_rel_ret    	all	4481


8: Standardwerte aber similarity_name = cosine
* kompletter Textkorpus
* Volltexte
* [-w=5,-t=0.7,-m=3,-s=cosine,-l=score]
num_ret        	all	103013
num_rel        	all	7437
num_rel_ret    	all	4882

38: Standardwerte aber similarity_name = cosine [-w=2,-t=1.0,-m=3,-s=cosine,-l=score]
* kompletter Textkorpus
* Volltexte
num_ret        	all	9288
num_rel        	all	7437
num_rel_ret    	all	4372

x9: Standardwerte aber similarity_name = overlap
* [-w=5,-t=0.7,-m=3,-s=overlap,-l=score]
* nur für 11597317.txt  
* Volltext
* für > 3 Mio. Konzepte -> Linux Fehler beim Versuch die datei zu schreiben
* dauert extrem lang
* es werden sehr viele Konmzepte gefunden
num_ret        	all	314252
num_rel        	all	12
num_rel_ret    	all	11


9: Standardwerte aber overlapping_criteria = length
* [-w=5,-t=0.7,-m=3,-s=jaccard,-l=length]
num_ret        	all	14077
num_rel        	all	7437
num_rel_ret    	all	4481

10.Nur Abstracts - Parameter wie 1.
* [-m=3,-s=jaccard,-t=0.7,-w=5,-l=score]
* Abstracts
* kompletter Korpus
### nicht normalisiert ###
num_ret        	all	2857
num_rel        	all	7437
num_rel_ret    	all	225
### normalisiert ###
num_ret        	all	2561
num_rel        	all	723
num_rel_ret    	all	79


11.Nur Abstracts - Parameter wie 3.
* [-m=4,-s=jaccard,-t=1.0,-w=5,-l=score]
* Abstracts
* kompletter Korpus
### nicht normalisiert ###
num_ret        	all	448
num_rel        	all	7437
num_rel_ret    	all	217
### normalisiert ###
num_ret        	all	170
num_rel        	all	723
num_rel_ret    	all	73


12.Nur Abstracts - Parameter wie 7.
* [-m=2,-s=jaccard,-t=0.7,-w=5,-l=score]
* Abstracts
* kompletter Korpus
### nicht normalisiert ###
num_ret        	all	2857
num_rel        	all	7437
num_rel_ret    	all	225
### normalisiert ###
num_ret        	all	2561
num_rel        	all	723
num_rel_ret    	all	79

40. {-m=3, -s=dice, -t=0.7, -w=5, -l=score}
* wie Stabdard aber similariry = dice
num_ret        	all	63308
num_rel        	all	7437
num_rel_ret    	all	4813

42. [-m=3,-s=jaccard,-t=0.6,-w=5,-l=score]
* nur Abstracts
* wie 8.
### nicht normalisiert ###
num_ret        	all	13492
num_rel        	all	7437
num_rel_ret    	all	247
### normalisiert ###


41. {-m=3, -s=dice, -t=0.9, -w=5, -l=score}
* wie 40 mit similariry = dice, aber 
num_ret        	all	9690
num_rel        	all	7437
num_rel_ret    	all	4410

43. [-m=3,-s=jaccard,-t=0.6,-w=5,-l=score]
* t=0.6 wie bei Soldaini als bester Recall beobachtet



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

13/44. Keine Parameter [--XMLf1,-R=NCBI]
* Volltexte
* ohne Disambiguiation
* Strict Data Model -> höhere Precision bei gleichen Recall (lt. Funke!!, vgl. vgl. 14 mit 15)
num_ret        	all	15715
num_rel        	all	7437
num_rel_ret    	all	5783


14/45. mit Disambiguation Server [--XMLf1,-R=NCBI,-DISAMB]
* Volltexte
* mit Disambiguiation
num_ret        	all	15715
num_rel        	all	7437
num_rel_ret    	all	5783



**15a. mit Disambiguation Server [--XMLf1,-R=NCBI,-o,--DISAMB]
* Volltexte
* mit Disambiguiation
* allow Overmatches
--> ! Resource error: insufficient memory


15/46. Relaxed Model [--XMLf1,-R=NCBI,-C,V=Base,--DISAMB]
* Volltexte
* Relaxed Model
* eigene DB installiert
* enthält mehr Strings u. a. mit Kunjunktionen (-> nochnal checken)
num_ret        	all	15514
num_rel        	all	7437
num_rel_ret    	all	5734


16/47. Relaxed Model [--XMLf1,-R=NCBI,-C,V=Base,-z,--DISAMB]
* Volltexte
* Kombination von Relaxed Model und Term Processing (empfohlen)
* Term processing: Process terms, i.e., short text fragments, rather than a document containing complete sentences.
* eher für Listen geeignet oder Aufzeählungen / Stichwortsammlungen etc.
num_ret        	all	13334
num_rel        	all	7437
num_rel_ret    	all	4523



17/48. Relaxed Model [--XMLf1,-R=NCBI,-t,-C,-V=Base,--DISAMB]
* Volltexte
* Bypass für part-of-speech-tagger
* Ergebnisse mit QuickUMLS vergleichbar ?? (Check!)
* geht ziemlich schnell
num_ret        	all	15007
num_rel        	all	7437
num_rel_ret    	all	5620


18/49. threshhold hochsetzen: [--XMLf1,-R=NCBI,-r=950,--DISAMB]
* Volltexte
num_ret        	all	2219
num_rel        	all	7437
num_rel_ret    	all	781


19/50. threshhold verringern: [--XMLf1,-R=NCBI,-r=800,--DISAMB]
* Volltexte
* eleminiert false positives
num_ret        	all	6016
num_rel        	all	7437
num_rel_ret    	all	2109


20/51. prefer multiple concepts: [--XMLf1,-R=NCBI,-Y,--DISAMB]
* Volltexte
* bewertet Mappings höher, die mehr Konzepte umfassen
* sinnvoll, um semantische Beziehungen zwischen den im Text gefundenen Konzept zu analysieren
num_ret        	all	15207
num_rel        	all	7437
num_rel_ret    	all	5610


21/52. composite phrases: [--XMLf1,-R=NCBI,-Q=0,--DISAMB]
* Volltexte
* keine Kompositionen von Phrasen mit Präpositionen generieren
* Tradeoff höhere Effizienz/geringere Qualität d. Ergebnisse
num_ret        	all	15107
num_rel        	all	7437
num_rel_ret    	all	5668

22/53. -no derivational variants: [--XMLf1,-R=NCBI,-d,--DISAMB]
* Volltexte
* keine aus Derivation gebildeten Varianten
* vermeidet Änderungen im Sinn
* evtl. Verbesserung der Precision?
num_ret        	all	14688
num_rel        	all	7437
num_rel_ret    	all	5612


23/54. threshhold verringern: [--XMLf1,-R=NCBI,-r=700,--DISAMB]
* Volltexte
* eleminiert false positives
num_ret        	all	9411
num_rel        	all	7437
num_rel_ret    	all	3374


24/55. Untere Threshholdgrenze aussetzen und alle Ergebnisse anzeigen...: [--XMLf1,r=0,-R=NCBI] wie 14??
* Volltexte
* Threshhold explizit auf 0 gesetzt
num_ret        	all	15114
num_rel        	all	7437
num_rel_ret    	all	5645

25/56. threshhold verringern: [--XMLf1,-R=NCBI,-r=600,--DISAMB]
* Volltexte
* eleminiert false positives
num_ret        	all	11728
num_rel        	all	7437
num_rel_ret    	all	4316


26/57. threshhold verringern: [--XMLf1,-R=NCBI,-r=400,--DISAMB]
* Volltexte
* eleminiert false positives
num_ret        	all	15114
num_rel        	all	7437
num_rel_ret    	all	5645


28/58. threshhold verringern: [--XMLf1,-R=NCBI,-r=200,--DISAMB]
* Volltexte
* eleminiert false positives
* Grenze bei 500?
num_ret        	all	15114
num_rel        	all	7437
num_rel_ret    	all	        5645


29/59. best F lt. Funk: [--XMLf1,-R=NCBI,-r=0,-d,-u]
* model ANY
* gaps NONE
* wordOrder Matters
* acronymAbb DEFAULT/UNIQUE
* derivationalVariants NONE
* scoreFilter 0/600
* (minTermSize 3)
* ohne DISAMB (gabs 2012 noch nicht?) -> aber wohl kein Effekt ohne DISAMB ??
num_ret        	all	14724
num_rel        	all	7437
num_rel_ret    	all	5612


30/60. höchster R lt. Funk: [--XMLf1,-R=NCBI,-C,-V=Base,-r=0,-g,-i,-a,-D,--DISAMB]
* model RELAXED
* gaps ALLOW
* wordOrder IGNORE
* acronymAbb ALL
* derivationalVariants ALL
* scoreFilter 0
* (minTermSize 1/3)
num_ret        	all	20101
num_rel        	all	7437
num_rel_ret    	all	5469


31/61. höchste P lt. Funke: [--XMLf1,-R=NCBI,-r=1000,-d,--DISAMB]
* model STRICT
* gaps NONE
* wordOrder Matters
* acronymAbb DEFAULT/UNIQUE
* derivationalVariants NONE
* scoreFilter 1000
* (minTermSize 3/5)
num_ret        	all	2167
num_rel        	all	7437
num_rel_ret    	all	776


32/62. wie 15 aber mit Abstracts [--XMLf1,-R=NCBI,--DISAMB]
* Abstracts
* keine fehlerbehafteten Ergebnis XMLs!!
* Dauer: 272 !!!
* noch weniger als bei Quick-UMLS
* Zeichen, dass MM versucht Phrasen im ganzen Text zueinander in Beziehung zu setzen (???)
### nicht normalisiert ###
num_ret        	all	743
num_rel        	all	7437
num_rel_ret    	all	349
### normalisiert ###
num_ret        	all	273
num_rel        	all	723
num_rel_ret    	all	121


33/63. mit blanklines [--XMLf1,-R=NCBI,--blanklines=0,--DISAMB]
* Abstracts
* bei Volltexten --> Memory Overflow
### nicht normalisiert ###
num_ret        	all	760
num_rel        	all	7437
num_rel_ret    	all	349
### normalisiert ##
num_ret        	all	273
num_rel        	all	723
num_rel_ret    	all	121


34/64. wie 21. aber mit Abstracts {-R=NCBI, -r=0, --DISAMB=, --XMLf1=}
* bester ecall bei Volltexten
### nicht normalisiert ###
num_ret        	all	738
num_rel        	all	7437
num_rel_ret    	all	349
### normalisiert ##
num_ret        	all	274
num_rel        	all	723
num_rel_ret    	all	122


35/65. wie 21. aber mit Abstracts {-R=NCBI, -r=0, --DISAMB=, --XMLf1=}
* bester ecall bei Volltexten
### nicht normalisiert ###
num_ret        	all	743
num_rel        	all	7437
num_rel_ret    	all	349
### normalisiert ##
num_ret        	all	273
num_rel        	all	723
num_rel_ret    	all	121


36/66. wie 21. aber mit blanklines und Abstracts [--XMLf1,-R=NCBI,-Q=0,r=400, --blanklines = 0,--DISAMB] 
* bester call bei Volltexten bisher
### nicht normalisiert ###
num_ret        	all	756
num_rel        	all	7437
num_rel_ret    	all	350
### normalisiert ##
num_ret        	all	274
num_rel        	all	723
num_rel_ret    	all	122



36/37/67. wie 21. aber mit blanklines und Volltexten [--XMLf1,-R=NCBI,-Q=0,-r=400,--blanklines=0,--DISAMB] 
* bester call bei Volltexten bisher
* für 37 keine TREC datei angelegt!
* wenn blanklines gesetzt wird in vielen Texten keine einzige Annotation gefunden!

39/68. [-R=NCBI,-r=0,--DISAMB,--XMLf1=]
* keine nennenswerten Erkenntnisse erwartet
* benötigt für den Vergleich mit Abstract Texte Performance
num_ret        	all	15114
num_rel        	all	7437
num_rel_ret    	all	5645

R4. mit Disambiguation Server [--XMLf1,-R=NCBI,-DISAMB]
* Volltexte
* Remote Server
* mit Disambiguiation
num_ret        	all	15114
num_rel        	all	7437
num_rel_ret    	all	5645


xx. • Negation Detection ??


xx. -g (--allow concept gaps) ???

xx. -i (--ignore word order) ???

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
* checken warum hier 2 Konzepte auf 1 Begriff gemappt werden
    <concepts_matched>
      <concept>
        <start>334</start>
        <ngram>mouse</ngram>
        <end>339</end>
        <preferred>1</preferred>
        <similarity>1.0</similarity>
        <semtypes>set([u'T015'])</semtypes>
        <term>mouse</term>
        <cui/>
        <id cui="C0025914"/>
      </concept>
      <concept>
        <start>334</start>
        <ngram>mouse</ngram>
        <end>339</end>
        <preferred>1</preferred>
        <similarity>1.0</similarity>
        <semtypes>set([u'T015'])</semtypes>
        <term>mouse</term>
        <cui/>
        <id cui="C0026809"/>
      </concept>
    </concepts_matched>

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
* warum in DB speicher?
** Auswertungen Konzepte über mehrere testRuns und Extraktoren z. B. hinweg
** Vorkommen einzelner Konzepte...
* ca 12 Konzepte wh. fehlendem CUI Mapping aus qrel datei aUSGESCHLOSSEN (0,002 % ?)


### To Do ###
* variieren: overlapping_criteria, similarity_name mit Standard Werten Sonst
* accepted semtypes verringern (Standard-Werte)? Einfluß auf Performance? ...Evaluation?




