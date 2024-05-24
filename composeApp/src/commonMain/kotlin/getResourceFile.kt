import io.kamel.core.utils.File

// This fun is handles the resources location in compose
public expect suspend fun getResourceFile(fileResourcePath: String): File
