/**
 * 
 */
package ti.modules.titanium.android;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiContext;
import org.appcelerator.titanium.util.Log;
import org.appcelerator.titanium.util.TiConfig;

import android.content.Context;
import android.content.Intent;

@Kroll.proxy(creatableInModule=AndroidModule.class)
public class ActivityProxy 
	extends KrollProxy
{
	private static final String LCAT = "TiActivity";
	private static boolean DBG = TiConfig.LOGD;
		
	//TODO This may need to be a soft reference.
	private TiBaseActivity activity;
	
	public ActivityProxy(TiContext tiContext) 
	{
		super(tiContext);
	}
	
	@Override
	public void handleCreationArgs(Object[] args) {
		super.handleCreationArgs(args);
		if (args != null && args.length >= 1) {
			if (args[0] instanceof TiBaseActivity) {
				if (DBG) {
					Log.d(LCAT, "ActivityProxy created with existing Activity");
				}
				activity = (TiBaseActivity) args[0];
			}
		}
	}
	
	@Kroll.method
	public void start(IntentProxy intentProxy) 
	{
		Intent intent = intentProxy.getIntent();
		this.getTiContext().getActivity().startActivity(intent);
	}
	
	protected Context getContext() {
		if (activity == null) {
			return getTiContext().getActivity().getApplication();
		}
		return activity;
	}
	
	protected TiBaseActivity getActivity() {
		return activity;
	}
	
	protected void release() {
		activity = null;
	}
}
