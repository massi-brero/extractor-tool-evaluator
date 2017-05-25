## 2. Extractors##

#### QuickUMLS####

1. Download and follow the instructions on ...

###cTakes###

#####XMI creating from console using the standard pipeline#####

bin/runClinicalPipeline.sh  -i input --xmiOut output  --user massibrero  --pass Slkdhnrda70! -l /home/massi/projects/resources/extractors/ctakes/apache-ctakes-4.0.0/resources/org/apache/ctakes/dictionary/lookup/fast/ncbi+go.xml

#####Customizing ctakes with own ontologies#####

1. You need a UMLS installation of RFF files.
2. Create custom dictionary with your ontology. use cTakes Dictionary Creator for that.
3. Customize an annotator (in /desc/ctakes-dictionary-lookup-fast or /desc/ctakes-dictionary-lookup-fast). Save an custom descriptor.
4. Customize a clinical pipeline descriptor like /home/massi/projects/resources/extractors/ctakes/apache-ctakes-4.0.0/desc/ctakes-clinical-pipeline/desc/analysis_engine/AggregatePlaintextUMLSProcessor.xml -> insert your custom annotator.
5. reduce pipeline steps to remove any not wanted analysis from yout output (and maybe speeding up the process...?)

Extremely important: Add the following type definition to the most primitive descriptor (or your custom annotator)
<typeSystemDescription>
	<imports>
        <import name="org.apache.ctakes.typesystem.types.TypeSystem"/>
	</imports>
</typeSystemDescription>





