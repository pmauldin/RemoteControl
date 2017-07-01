package org.remotecontrol

import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonView)
class DesktopView {
    @MVCMember @Nonnull
    FactoryBuilderSupport builder
    @MVCMember @Nonnull
    DesktopModel model

    void initUI() {
        builder.application(title: application.configuration['application.title'],
            sizeToScene: true, centerOnScreen: true, name: 'mainWindow') {
            scene(fill: WHITE, width: 200, height: 60) {
                gridPane {
                    label(id: 'clickLabel', row: 0, column: 0,
                          text: bind(model.clickCountProperty()))
                    button(row: 1, column: 0, prefWidth: 200,
                           id: 'clickActionTarget', clickAction)
                }
            }
        }
    }
}