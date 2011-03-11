package org.dromdary.jpa.generator.model2model;

import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.WorkflowComponentWithModelSlot;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;

public abstract class SimpleJavaModificationComponent extends
		WorkflowComponentWithModelSlot {

	private Object modelObject;

	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues) {

		String strName = getModelSlot();
		modelObject = ctx.get(strName);

		if (modelObject == null) {
			issues.addWarning(this, "content of modelSlot " + getModelSlot()
					+ " is null.");
		}

		doModification(ctx, monitor, issues, modelObject);
	}

	protected abstract void doModification(WorkflowContext ctx,
			ProgressMonitor monitor, Issues issues, Object model);

}
