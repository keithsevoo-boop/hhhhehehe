package com.prismos.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends Activity {
  LinearLayout root, content; boolean dark=true;
  int bg(){return dark?Color.rgb(12,15,32):Color.rgb(233,238,251);}
  int fg(){return dark?Color.WHITE:Color.rgb(23,27,49);}
  int muted(){return dark?0xffb5b8d4:0xff646b84;}
  int dp(float n){return (int)(n*getResources().getDisplayMetrics().density+.5f);}
  GradientDrawable rounded(int color,int r){GradientDrawable d=new GradientDrawable();d.setColor(color);d.setCornerRadius(dp(r));return d;}
  TextView text(String s,int size,int color){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);return t;}
  void pad(View v,int a,int b,int c,int d){v.setPadding(dp(a),dp(b),dp(c),dp(d));}
  @Override public void onCreate(Bundle b){super.onCreate(b);home();}
  void shell(String heading){
    root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setBackgroundColor(bg());setContentView(root);
    ScrollView scroll=new ScrollView(this);scroll.setFillViewport(true);root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
    content=new LinearLayout(this);content.setOrientation(LinearLayout.VERTICAL);pad(content,22,36,22,24);scroll.addView(content);
    TextView title=text(heading,28,fg());title.setTypeface(null,Typeface.BOLD);content.addView(title);
  }
  void home(){shell("PrismOS  ✦");
    TextView subtitle=text("Liquid light. Limitless possibilities.",13,muted());pad(subtitle,0,3,0,25);content.addView(subtitle);
    LinearLayout hero=new LinearLayout(this);hero.setOrientation(LinearLayout.VERTICAL);pad(hero,22,24,22,24);
    GradientDrawable grad=new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{0xff5745d9,0xff2a75b9,0xffbe4ba9});grad.setCornerRadius(dp(30));hero.setBackground(grad);content.addView(hero);
    String date=new SimpleDateFormat("EEEE, MMMM d",Locale.getDefault()).format(new Date());TextView dateText=text(date,16,Color.WHITE);hero.addView(dateText);
    TextView clock=text(new SimpleDateFormat("HH:mm",Locale.getDefault()).format(new Date()),55,Color.WHITE);clock.setTypeface(Typeface.DEFAULT,Typeface.BOLD);hero.addView(clock);
    TextView info=text("PR652B  •  PrismOS Preview 1.0",12,0xffe7e5ff);pad(info,0,12,0,0);hero.addView(info);
    TextView label=text("YOUR SPACE",12,muted());pad(label,0,26,0,14);content.addView(label);
    LinearLayout buttons=new LinearLayout(this);buttons.setOrientation(LinearLayout.HORIZONTAL);content.addView(buttons);
    tile(buttons,"◉","All apps",()->apps());tile(buttons,"⚙","Settings",()->startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS)));
    LinearLayout buttons2=new LinearLayout(this);buttons2.setOrientation(LinearLayout.HORIZONTAL);content.addView(buttons2);
    tile(buttons2,"✦","Prism Control",()->control());tile(buttons2,"◐","Appearance",()->{dark=!dark;home();});
    TextView foot=text("PrismOS is a launcher preview. Android 11 and XOS remain installed.",12,muted());pad(foot,0,24,0,0);content.addView(foot);
    LinearLayout dock=new LinearLayout(this);dock.setGravity(Gravity.CENTER);pad(dock,10,12,10,12);dock.setBackground(rounded(dark?0xff292d49:0xffd3daef,26));LinearLayout.LayoutParams dockP=new LinearLayout.LayoutParams(-1,-2);dockP.topMargin=dp(28);content.addView(dock,dockP);
    dockItem(dock,"⌂",()->home());dockItem(dock,"▦",()->apps());dockItem(dock,"⚙",()->startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS)));
  }
  void tile(LinearLayout row,String icon,String name,Runnable action){LinearLayout b=new LinearLayout(this);b.setOrientation(LinearLayout.VERTICAL);b.setGravity(Gravity.CENTER);pad(b,8,20,8,20);b.setBackground(rounded(dark?0xff252a47:0xffdce3f7,24));LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,dp(124),1);lp.setMargins(dp(4),dp(4),dp(4),dp(4));row.addView(b,lp);TextView ico=text(icon,30,fg());ico.setGravity(Gravity.CENTER);b.addView(ico);TextView t=text(name,14,fg());t.setGravity(Gravity.CENTER);pad(t,0,12,0,0);b.addView(t);b.setOnClickListener(v->action.run());}
  void dockItem(LinearLayout dock,String s,Runnable r){TextView t=text(s,28,fg());t.setGravity(Gravity.CENTER);dock.addView(t,new LinearLayout.LayoutParams(0,dp(40),1));t.setOnClickListener(v->r.run());}
  void back(){TextView b=text("‹ Back to Home",15,muted());pad(b,0,0,0,20);content.addView(b,0);b.setOnClickListener(v->home());}
  void apps(){shell("All apps");back();PackageManager pm=getPackageManager();Intent intent=new Intent(Intent.ACTION_MAIN);intent.addCategory(Intent.CATEGORY_LAUNCHER);List<ResolveInfo> list=pm.queryIntentActivities(intent,0);Collections.sort(list,new ResolveInfo.DisplayNameComparator(pm));
    for(ResolveInfo ri:list){if(ri.activityInfo==null)continue;LinearLayout row=new LinearLayout(this);row.setGravity(Gravity.CENTER_VERTICAL);pad(row,12,12,12,12);LinearLayout.LayoutParams rp=new LinearLayout.LayoutParams(-1,-2);rp.bottomMargin=dp(6);content.addView(row,rp);row.setBackground(rounded(dark?0xff242942:0xffdce3f7,16));ImageView image=new ImageView(this);try{Drawable icon=ri.loadIcon(pm);image.setImageDrawable(icon);}catch(Exception ignored){}row.addView(image,new LinearLayout.LayoutParams(dp(38),dp(38)));TextView t=text(ri.loadLabel(pm).toString(),16,fg());pad(t,14,0,0,0);row.addView(t);row.setOnClickListener(v->{try{Intent launch=pm.getLaunchIntentForPackage(ri.activityInfo.packageName);if(launch!=null)startActivity(launch);}catch(Exception e){Toast.makeText(this,"Unable to open app",Toast.LENGTH_SHORT).show();}});}
  }
  void control(){shell("Prism Control");back();TextView t=text("Quick access to your Android controls",14,muted());pad(t,0,6,0,22);content.addView(t);LinearLayout r=new LinearLayout(this);content.addView(r);tile(r,"◉","Wi-Fi",()->startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS)));tile(r,"ᛒ","Bluetooth",()->startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS)));LinearLayout r2=new LinearLayout(this);content.addView(r2);tile(r2,"☼","Display",()->startActivity(new Intent(android.provider.Settings.ACTION_DISPLAY_SETTINGS)));tile(r2,"♫","Sound",()->startActivity(new Intent(android.provider.Settings.ACTION_SOUND_SETTINGS)));TextView note=text("Controls open the genuine Android settings. System-level toggles require additional integration.",12,muted());pad(note,0,20,0,0);content.addView(note);}
  @Override public void onBackPressed(){home();}
}
