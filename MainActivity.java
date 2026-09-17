package com.solbetgames.app;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.*;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends Activity {
    int gold=Color.rgb(255,193,7), bg=Color.rgb(7,16,22), panel=Color.rgb(13,27,36), white=Color.WHITE, muted=Color.rgb(154,170,181), green=Color.rgb(32,201,151), red=Color.rgb(255,92,92);
    LinearLayout root, content, nav;
    android.content.SharedPreferences prefs;
    final String PREF_EXPIRY="vip_expiry";

    @Override public void onCreate(Bundle b){ super.onCreate(b); prefs=getSharedPreferences("solbet",0); showHome(); }

    TextView tv(String text,float size,int color){ TextView t=new TextView(this); t.setText(text); t.setTextSize(size); t.setTextColor(color); t.setPadding(18,12,18,12); return t; }
    Button btn(String text){ Button b=new Button(this); b.setText(text); b.setTextColor(Color.BLACK); b.setTextSize(14); b.setAllCaps(false); b.setBackground(round(gold,14)); return b; }
    GradientDrawable round(int color,int r){ GradientDrawable g=new GradientDrawable(); g.setColor(color); g.setCornerRadius(r); return g; }
    LinearLayout box(){ LinearLayout l=new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); l.setPadding(16,14,16,14); l.setBackground(round(panel,18)); LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2); p.setMargins(12,8,12,8); l.setLayoutParams(p); return l; }
    void base(String title){
        root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setBackgroundColor(bg);
        LinearLayout head=new LinearLayout(this); head.setGravity(Gravity.CENTER_VERTICAL); head.setPadding(10,8,10,4);
        TextView menu=tv("☰",26,white); head.addView(menu,new LinearLayout.LayoutParams(50,60));
        TextView h=tv(title,21,white); h.setTypeface(null,1); head.addView(h,new LinearLayout.LayoutParams(0,60,1));
        TextView bell=tv("♧",24,gold); head.addView(bell,new LinearLayout.LayoutParams(50,60)); root.addView(head);
        ScrollView sv=new ScrollView(this); content=new LinearLayout(this); content.setOrientation(LinearLayout.VERTICAL); content.setPadding(4,4,4,12); sv.addView(content); root.addView(sv,new LinearLayout.LayoutParams(-1,0,1));
        nav=new LinearLayout(this); nav.setGravity(Gravity.CENTER); nav.setPadding(2,4,2,4); nav.setBackgroundColor(Color.rgb(5,12,17));
        addNav("⌂","Home"); addNav("⚽","Tips"); addNav("♛","VIP"); addNav("🎮","Games"); addNav("◉","Results"); addNav("☺","Account"); root.addView(nav,new LinearLayout.LayoutParams(-1,72)); setContentView(root);
    }
    void addNav(String icon,String label){ LinearLayout item=new LinearLayout(this); item.setOrientation(LinearLayout.VERTICAL); item.setGravity(Gravity.CENTER); TextView a=tv(icon,19,white); a.setGravity(Gravity.CENTER); TextView b=tv(label,10,muted); b.setGravity(Gravity.CENTER); item.addView(a); item.addView(b); item.setOnClickListener(v->{ if(label.equals("Home"))showHome(); else if(label.equals("Tips"))showTips(); else if(label.equals("VIP"))showVip(); else if(label.equals("Games"))showGames(); else if(label.equals("Results"))showResults(); else showAccount(); }); nav.addView(item,new LinearLayout.LayoutParams(0,-1,1)); }
    void hero(String title,String sub){ LinearLayout l=box(); TextView a=tv(title,25,white); a.setTypeface(null,1); l.addView(a); TextView s=tv(sub,13,muted); l.addView(s); content.addView(l); }
    void addTip(String match,String league,String pick,String odds,boolean vip){ LinearLayout l=box(); LinearLayout top=new LinearLayout(this); TextView m=tv(match,16,white); m.setTypeface(null,1); top.addView(m,new LinearLayout.LayoutParams(0,-2,1)); TextView tag=tv(vip?"VIP":"NORMAL",11,vip?gold:green); tag.setGravity(Gravity.CENTER); tag.setBackground(round(vip?Color.rgb(72,58,0):Color.rgb(0,70,50),12)); top.addView(tag,new LinearLayout.LayoutParams(82,38)); l.addView(top); l.addView(tv(league,11,muted)); TextView p=tv(pick+"   •   Odds "+odds,15,gold); p.setTypeface(null,1); l.addView(p); l.addView(tv("Risk applies. Tips are not guaranteed.",10,muted)); content.addView(l); }
    void showHome(){ base("SOL-BET GAMES"); hero("BET SMART • WIN BIG","Sports tips • VIP selections • Games coming soon");
        LinearLayout cards=new LinearLayout(this); cards.setPadding(8,4,8,4); cards.addView(btn("⚽ Normal Tips"),new LinearLayout.LayoutParams(0,58,1)); Button v=btn("♛ VIP Tips"); v.setOnClickListener(x->showVip()); cards.addView(v,new LinearLayout.LayoutParams(0,58,1)); content.addView(cards);
        TextView hd=tv("Today's Top Tips",18,white); hd.setTypeface(null,1); content.addView(hd); addTip("Man City vs Arsenal","Premier League","1X & Over 1.5","1.65",false); addTip("Real Madrid vs Barcelona","La Liga","BTTS - Yes","1.72",false); addTip("AC Milan vs Inter Milan","Serie A","Under 2.5","1.60",false);
        hero("🎮 Games — Coming Soon","Aviator, Crash, Slots and other games can be added later after the app grows."); }
    void showTips(){ base("Sports Tips"); hero("Normal Tips","Free selections for all users"); addTip("Man City vs Arsenal","Premier League","1X & Over 1.5","1.65",false); addTip("Real Madrid vs Barcelona","La Liga","BTTS - Yes","1.72",false); addTip("Bayern Munich vs Dortmund","Bundesliga","1X & Over 1.5","1.70",false); addTip("Chelsea vs Liverpool","Premier League","BTTS - Yes","1.68",false); }
    boolean vipActive(){ return prefs.getLong(PREF_EXPIRY,0)>System.currentTimeMillis(); }
    String expiry(){ long x=prefs.getLong(PREF_EXPIRY,0); if(x==0)return "Not active"; return new SimpleDateFormat("dd MMM yyyy",Locale.getDefault()).format(new Date(x)); }
    void showVip(){ base("VIP Tips"); if(vipActive()){ hero("♛ VIP MEMBERS AREA","Access active until "+expiry()); addTip("Napoli vs Lazio","Serie A","1X & Over 1.5","1.85",true); addTip("PSG vs Marseille","Ligue 1","BTTS - Yes","1.80",true); addTip("Atletico Madrid vs Sevilla","La Liga","Over 2.5","1.95",true); } else { hero("♛ VIP MEMBERS ONLY","Monthly subscription: KSh 1,000"); TextView lock=tv("🔒 VIP content is locked.\n\nPay KSh 1,000 for one month of VIP access. When the month expires, access locks automatically until the subscription is renewed.",16,white); lock.setPadding(22,28,22,28); content.addView(lock); Button pay=btn("Subscribe — KSh 1,000 / month"); pay.setOnClickListener(v->showPaymentInfo()); content.addView(pay,new LinearLayout.LayoutParams(-1,58)); content.addView(tv("Payment integration will be connected in the next build. This prototype does not process real payments.",11,muted)); } }
    void showPaymentInfo(){ new AlertDialog.Builder(this).setTitle("VIP Subscription").setMessage("SOL-BET GAMES VIP\n\nKSh 1,000 / 30 days\n\nNext step: connect your preferred payment provider (for example M-Pesa) so successful payments automatically activate VIP.").setPositiveButton("OK",null).show(); }
    void showGames(){ base("Games"); hero("🎮 Games","Future expansion area"); addGame("✈ Aviator","Coming Soon"); addGame("🚀 Crash","Coming Soon"); addGame("🎰 Slots","Coming Soon"); addGame("🃏 Live Casino","Coming Soon"); addGame("🎲 Other Games","Coming Soon"); }
    void addGame(String name,String status){ LinearLayout l=box(); TextView n=tv(name,18,white); n.setTypeface(null,1); l.addView(n); l.addView(tv(status,12,gold)); content.addView(l); }
    void showResults(){ base("Results"); hero("Performance History","Published selections and settled results"); result("Man City vs Arsenal","1X & Over 1.5","WON","1.65",green); result("Real Madrid vs Barcelona","BTTS - Yes","WON","1.72",green); result("AC Milan vs Inter Milan","Under 2.5","LOST","1.60",red); result("Bayern Munich vs Dortmund","1X & Over 1.5","WON","1.70",green); }
    void result(String m,String p,String r,String o,int c){ LinearLayout l=box(); TextView a=tv(m,15,white); a.setTypeface(null,1); l.addView(a); l.addView(tv(p+" • "+o,13,gold)); l.addView(tv(r,12,c)); content.addView(l); }
    void showAccount(){ base("My Account"); hero("John Doe","john@example.com"); LinearLayout l=box(); l.addView(tv("VIP Status",15,white)); l.addView(tv(vipActive()?"ACTIVE — expires "+expiry():"INACTIVE",16,vipActive()?green:red)); content.addView(l); Button b=btn(vipActive()?"VIP Active":"Get VIP — KSh 1,000 / month"); b.setOnClickListener(v->showVip()); content.addView(b,new LinearLayout.LayoutParams(-1,58)); content.addView(tv("Settings\nNotifications\nTerms & Conditions\nPrivacy Policy",15,white)); }
}
