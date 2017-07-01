package org.remotecontrol

import griffon.core.artifact.GriffonModel
import griffon.transform.FXObservable
import griffon.metadata.ArtifactProviderFor

@ArtifactProviderFor(GriffonModel)
class DesktopModel {
    @FXObservable String clickCount = "0"
}