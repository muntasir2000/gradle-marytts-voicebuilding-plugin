package de.dfki.mary.voicebuilding.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

@ParallelizableTask
class PraatExec extends DefaultTask {

    @Input
    String command = 'praat'

    @InputFile
    File scriptFile

    @Optional
    @Input
    Map props = [:]

    @Optional
    @InputFile
    File srcFile

    @Optional
    @OutputFile
    File destFile

    @TaskAction
    void exec() {
        project.copy {
            from scriptFile
            into temporaryDir
            expand props
        }
        project.exec {
            commandLine command, '--run', scriptFile.name
            workingDir temporaryDir
        }
    }
}
