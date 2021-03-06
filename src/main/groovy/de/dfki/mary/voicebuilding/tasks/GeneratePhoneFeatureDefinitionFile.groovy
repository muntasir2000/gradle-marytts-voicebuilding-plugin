package de.dfki.mary.voicebuilding.tasks

import marytts.features.FeatureDefinition
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class GeneratePhoneFeatureDefinitionFile extends DefaultTask {

    @InputDirectory
    final DirectoryProperty srcDir = newInputDirectory()

    @Input
    Property<String> srcExt = project.objects.property(String)

    @OutputFile
    final RegularFileProperty destFile = newOutputFile()

    @TaskAction
    void generate() {
        def featureDefinition
        project.fileTree(srcDir).include("*.${srcExt.get()}").first().withReader('UTF8') { src ->
            featureDefinition = new FeatureDefinition(src, false)
        }
        destFile.get().asFile.withPrintWriter('UTF-8') { dest ->
            featureDefinition.generateFeatureWeightsFile(dest)
        }
    }
}
