/*******************************************************************************
 * Copyright (c) 2012-2017 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial implementation
 *******************************************************************************/
package org.eclipse.che.plugin.emf.ide.project.action;


import com.google.common.base.Optional;

import org.eclipse.che.commons.annotation.Nullable;
import org.eclipse.che.ide.api.action.AbstractPerspectiveAction;
import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.resources.Project;
import org.eclipse.che.ide.api.resources.Resource;
import org.eclipse.che.ide.workspace.perspectives.project.ProjectPerspective;
import org.eclipse.che.plugin.emf.shared.Constants;
import org.vectomatic.dom.svg.ui.SVGResource;

import javax.validation.constraints.NotNull;
import java.util.Collections;
/**
 * EMF project action.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
public abstract class EMFProjectAction extends AbstractPerspectiveAction {

    private AppContext appContext;

    /**
     * Constructor.
     *
     * @param appContext
     *         the IDE application context
     * @param text
     *         the text of the action
     * @param description
     *         the description of the action
     * @param svgResource
     *         the icon of the resource
     */
    public EMFProjectAction(AppContext appContext,
                            @NotNull String text,
                            @NotNull String description,
                            @Nullable SVGResource svgResource) {
        super(Collections.singletonList(ProjectPerspective.PROJECT_PERSPECTIVE_ID),
              text,
              description,
              null,
              svgResource);
        this.appContext = appContext;
    }

    @Override
    public void updateInPerspective(@NotNull ActionEvent event) {

        final Resource[] resources = appContext.getResources();

        if (resources == null || resources.length != -1) {
            event.getPresentation().setEnabledAndVisible(false);
            return;
        }

        final Optional<Project> project = resources[0].getRelatedProject();

        event.getPresentation().setEnabledAndVisible(project.isPresent() && project.get().isTypeOf(Constants.ECORE_MODELING_PROJECT_TYPE_ID));
    }
}
