import { useState } from "react";

const font = "'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif";

const mockTransactions = [
  { id: 1, name: "Whole Foods", category: "Groceries", amount: -67.42, date: "Feb 28", icon: "🛒" },
  { id: 2, name: "Shell Gas", category: "Gas", amount: -45.00, date: "Feb 27", icon: "⛽" },
  { id: 3, name: "Paycheck", category: "Income", amount: 1850.00, date: "Feb 25", icon: "💰" },
  { id: 4, name: "Netflix", category: "Subscriptions", amount: -15.99, date: "Feb 24", icon: "📺" },
  { id: 5, name: "Starbucks", category: "Dining", amount: -8.75, date: "Feb 23", icon: "☕" },
  { id: 6, name: "Amazon", category: "Shopping", amount: -34.99, date: "Feb 22", icon: "📦" },
];

const mockSubs = [
  { name: "Netflix", due: "Mar 5", amount: 15.99, icon: "📺", color: "#e50914" },
  { name: "Spotify", due: "Mar 8", amount: 9.99, icon: "🎵", color: "#1db954" },
  { name: "Apple iCloud", due: "Mar 10", amount: 2.99, icon: "☁️", color: "#888" },
  { name: "Xbox Game Pass", due: "Mar 15", amount: 14.99, icon: "🎮", color: "#107c10" },
  { name: "Hulu", due: "Mar 18", amount: 7.99, icon: "📱", color: "#1ce783" },
];

const aiInsights = [
  { type: "warning", emoji: "⚠️", title: "Coffee spending up 40%", body: "You've spent $52 at Starbucks this month — that's $18 more than last month. Consider brewing at home 3x/week to save ~$40/month.", tag: "Dining" },
  { type: "tip", emoji: "💡", title: "Subscription overlap detected", body: "You're paying for both Hulu and Netflix. Based on your watch history patterns, you've only used Netflix this month. Consider pausing Hulu to save $7.99.", tag: "Subscriptions" },
  { type: "praise", emoji: "🎉", title: "Great job on groceries!", body: "Your grocery spending is $23 under your $90 budget this month. Keep it up — you're on track to hit your savings goal 2 weeks early.", tag: "Groceries" },
  { type: "warning", emoji: "📉", title: "Savings goal at risk", body: "At your current spending rate, you'll fall $120 short of your $500 March savings goal. Cutting dining out once this week would close the gap.", tag: "Budget" },
];

const categoryColors = {
  Groceries: "#4ade80",
  Gas: "#fb923c",
  Income: "#60a5fa",
  Subscriptions: "#c084fc",
  Dining: "#f472b6",
  Shopping: "#facc15",
};

// ── SVG Nav Icons ──────────────────────────────────────
const HomeIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M3 9l9-7 9 7v11a2 2 0 01-2 2H5a2 2 0 01-2-2z"/>
    <polyline points="9 22 9 12 15 12 15 22"/>
  </svg>
);
const TrackIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <line x1="18" y1="20" x2="18" y2="10"/>
    <line x1="12" y1="20" x2="12" y2="4"/>
    <line x1="6" y1="20" x2="6" y2="14"/>
  </svg>
);
const SubsIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <polyline points="17 1 21 5 17 9"/>
    <path d="M3 11V9a4 4 0 014-4h14"/>
    <polyline points="7 23 3 19 7 15"/>
    <path d="M21 13v2a4 4 0 01-4 4H3"/>
  </svg>
);
const BudgetIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M21.21 15.89A10 10 0 118 2.83"/>
    <path d="M22 12A10 10 0 0012 2v10z"/>
  </svg>
);
const AIIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/>
  </svg>
);

// ── Transaction icon with colored background ────────────
const TxIcon = ({ icon, category, color }) => {
  const c = color || categoryColors[category] || "#7c3aed";
  return (
    <div style={{
      width: 42, height: 42, borderRadius: 13, flexShrink: 0,
      background: c + "18",
      border: `1px solid ${c}30`,
      display: "flex", alignItems: "center", justifyContent: "center",
      fontSize: 19,
    }}>{icon}</div>
  );
};

export default function App() {
  const [screen, setScreen] = useState("login");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [showUpload, setShowUpload] = useState(false);
  const [showManual, setShowManual] = useState(false);
  const [manualAmount, setManualAmount] = useState("");
  const [manualName, setManualName] = useState("");
  const [manualCat, setManualCat] = useState("Groceries");
  const [savingsGoal] = useState(500);
  const [savedSoFar] = useState(312);
  const [budget] = useState(800);
  const [spent] = useState(587);
  const [linkedBank, setLinkedBank] = useState(false);

  const nav = (s) => setScreen(s);

  const navItems = [
    { s: "home", icon: <HomeIcon />, label: "Home" },
    { s: "tracker", icon: <TrackIcon />, label: "Track" },
    { s: "subscriptions", icon: <SubsIcon />, label: "Subs" },
    { s: "budget", icon: <BudgetIcon />, label: "Budget" },
    { s: "ai-tips", icon: <AIIcon />, label: "AI Tips" },
  ];

  const NavBar = () => (
    <div style={{
      position: "fixed", bottom: 0, left: "50%", transform: "translateX(-50%)",
      width: 390,
      background: "rgba(8,8,16,0.92)", backdropFilter: "blur(20px)",
      borderTop: "1px solid #ffffff08",
      display: "flex", justifyContent: "space-around", padding: "10px 0 24px",
      zIndex: 100,
    }}>
      {navItems.map(({ s, icon, label }) => (
        <button key={s} onClick={() => nav(s)} style={{
          background: "none", border: "none", cursor: "pointer",
          display: "flex", flexDirection: "column", alignItems: "center", gap: 5,
          color: screen === s ? "#a78bfa" : "#333345",
          transition: "color 0.2s", padding: "4px 10px",
          fontFamily: font,
        }}>
          {icon}
          <span style={{
            fontSize: 10, letterSpacing: 0.4,
            fontWeight: screen === s ? 600 : 400,
          }}>{label}</span>
        </button>
      ))}
    </div>
  );

  const Shell = ({ children, title }) => (
    <div style={{
      width: 390, minHeight: 844, background: "#080810", color: "#e8e8f0",
      fontFamily: font, position: "relative", overflow: "hidden",
      paddingBottom: 80,
    }}>
      <div style={{
        position: "absolute", top: -80, left: -80, width: 320, height: 320,
        borderRadius: "50%", background: "radial-gradient(circle, #7c3aed08 0%, transparent 70%)",
        pointerEvents: "none",
      }} />
      <div style={{ padding: "56px 24px 16px", position: "relative" }}>
        <div style={{
          fontSize: 10, color: "#a78bfa", letterSpacing: 3,
          textTransform: "uppercase", marginBottom: 6, fontWeight: 600,
        }}>FinTrack</div>
        <div style={{ fontSize: 24, fontWeight: 700, color: "#f0f0f8", letterSpacing: -0.5 }}>{title}</div>
      </div>
      <div style={{ padding: "0 24px" }}>{children}</div>
      <NavBar />
    </div>
  );

  // ── LOGIN ──────────────────────────────────────────────
  if (screen === "login") return (
    <div style={{
      width: 390, minHeight: 844, background: "#080810",
      display: "flex", flexDirection: "column", alignItems: "center",
      justifyContent: "center", fontFamily: font, color: "#e8e8f0",
      padding: 32, boxSizing: "border-box", position: "relative", overflow: "hidden",
    }}>
      {/* Background glows */}
      <div style={{
        position: "absolute", top: "15%", left: "50%", transform: "translateX(-50%)",
        width: 320, height: 320, borderRadius: "50%",
        background: "radial-gradient(circle, #7c3aed16 0%, transparent 70%)",
        pointerEvents: "none",
      }} />
      <div style={{
        position: "absolute", bottom: "10%", right: "-15%",
        width: 200, height: 200, borderRadius: "50%",
        background: "radial-gradient(circle, #60a5fa0c 0%, transparent 70%)",
        pointerEvents: "none",
      }} />

      <div style={{
        width: 70, height: 70, borderRadius: 22,
        background: "linear-gradient(135deg, #6d28d9, #8b5cf6)",
        display: "flex", alignItems: "center", justifyContent: "center",
        fontSize: 30, marginBottom: 28,
        boxShadow: "0 0 0 1px #ffffff10, 0 0 48px #7c3aed55, 0 8px 32px rgba(0,0,0,0.5)",
      }}>✦</div>

      <div style={{
        fontSize: 10, color: "#a78bfa", letterSpacing: 3,
        textTransform: "uppercase", marginBottom: 10, fontWeight: 600,
      }}>FinTrack</div>
      <div style={{ fontSize: 26, fontWeight: 700, marginBottom: 6, letterSpacing: -0.5 }}>Welcome back</div>
      <div style={{ fontSize: 14, color: "#444", marginBottom: 44 }}>
        Your personal finance companion
      </div>

      <div style={{ width: "100%", display: "flex", flexDirection: "column", gap: 12 }}>
        <input
          placeholder="Email or username"
          value={username}
          onChange={e => setUsername(e.target.value)}
          style={{
            background: "#0f0f18", border: "1px solid #1c1c2a", borderRadius: 14,
            padding: "15px 18px", color: "#e8e8f0", fontSize: 15, fontFamily: font,
            outline: "none", width: "100%",
          }}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={e => setPassword(e.target.value)}
          style={{
            background: "#0f0f18", border: "1px solid #1c1c2a", borderRadius: 14,
            padding: "15px 18px", color: "#e8e8f0", fontSize: 15, fontFamily: font,
            outline: "none", width: "100%",
          }}
        />
        <button onClick={() => nav("home")} style={{
          background: "linear-gradient(135deg, #6d28d9, #8b5cf6)", border: "none",
          borderRadius: 14, padding: "16px", color: "#fff", fontSize: 15,
          fontFamily: font, fontWeight: 600, cursor: "pointer",
          marginTop: 4, boxShadow: "0 4px 24px #7c3aed44, 0 0 0 1px #ffffff0c",
          letterSpacing: 0.2,
        }}>Sign In</button>
        <button onClick={() => nav("home")} style={{
          background: "none", border: "1px solid #1c1c2a", borderRadius: 14,
          padding: "15px", color: "#555", fontSize: 14, fontFamily: font, cursor: "pointer",
        }}>Create Account</button>
      </div>
      <div style={{ marginTop: 28, fontSize: 12, color: "#252530", textAlign: "center" }}>
        No bank info required to get started
      </div>
    </div>
  );

  // ── HOME ───────────────────────────────────────────────
  if (screen === "home") return (
    <Shell title="Overview">
      {/* Balance card */}
      <div style={{
        background: "linear-gradient(135deg, #131328 0%, #0e1828 100%)",
        borderRadius: 22, padding: "22px 22px 20px",
        marginBottom: 14, border: "1px solid #ffffff07",
        position: "relative", overflow: "hidden",
        boxShadow: "0 8px 32px rgba(0,0,0,0.5)",
      }}>
        <div style={{
          position: "absolute", top: -50, right: -50, width: 180, height: 180,
          borderRadius: "50%",
          background: "radial-gradient(circle, #7c3aed1a 0%, transparent 70%)",
          pointerEvents: "none",
        }} />
        <div style={{ fontSize: 10, color: "#555", letterSpacing: 2.5, marginBottom: 10, fontWeight: 600 }}>MONTHLY BALANCE</div>
        <div style={{ fontSize: 40, fontWeight: 700, color: "#f0f0f8", marginBottom: 4, letterSpacing: -1 }}>
          $1,262<span style={{ fontSize: 22, color: "#555", fontWeight: 400 }}>.58</span>
        </div>
        <div style={{ fontSize: 13, color: "#4ade80", marginBottom: 20, display: "flex", alignItems: "center", gap: 5 }}>
          <span>↑</span> $84.20 from last month
        </div>
        <div style={{ display: "flex" }}>
          {[
            { label: "INCOME", val: "$1,850", color: "#60a5fa" },
            { label: "SPENT", val: "$587", color: "#f472b6" },
            { label: "SAVED", val: "$312", color: "#4ade80" },
          ].map(({ label, val, color }, i, arr) => (
            <div key={label} style={{
              flex: 1,
              paddingRight: i < arr.length - 1 ? 16 : 0,
              borderRight: i < arr.length - 1 ? "1px solid #ffffff07" : "none",
              marginRight: i < arr.length - 1 ? 16 : 0,
            }}>
              <div style={{ fontSize: 9, color: "#444", letterSpacing: 1.5, marginBottom: 5, fontWeight: 600 }}>{label}</div>
              <div style={{ fontSize: 17, color, fontWeight: 600 }}>{val}</div>
            </div>
          ))}
        </div>
      </div>

      {/* AI tip preview */}
      <div onClick={() => nav("ai-tips")} style={{
        background: "linear-gradient(135deg, #110a28 0%, #0a101e 100%)",
        borderRadius: 16, padding: "14px 16px",
        border: "1px solid #2d1b6918", marginBottom: 14, cursor: "pointer",
        display: "flex", alignItems: "center", gap: 12,
      }}>
        <div style={{
          width: 38, height: 38, borderRadius: 11,
          background: "linear-gradient(135deg, #6d28d9, #8b5cf6)",
          display: "flex", alignItems: "center", justifyContent: "center", fontSize: 17,
          flexShrink: 0, boxShadow: "0 0 16px #7c3aed44",
        }}>✦</div>
        <div style={{ flex: 1 }}>
          <div style={{ fontSize: 10, color: "#a78bfa", marginBottom: 3, letterSpacing: 1.5, fontWeight: 600 }}>AI INSIGHT</div>
          <div style={{ fontSize: 13, color: "#b0b0c8" }}>Coffee spending up 40% this month</div>
        </div>
        <span style={{ color: "#333348", fontSize: 18, lineHeight: 1 }}>›</span>
      </div>

      {/* Savings goal */}
      <div style={{
        background: "#0f0f18", borderRadius: 16, padding: 16,
        border: "1px solid #ffffff05", marginBottom: 14,
      }}>
        <div style={{ display: "flex", justifyContent: "space-between", marginBottom: 10 }}>
          <span style={{ fontSize: 13, color: "#777", fontWeight: 500 }}>March Savings Goal</span>
          <span style={{ fontSize: 13, color: "#4ade80", fontWeight: 600 }}>${savedSoFar} / ${savingsGoal}</span>
        </div>
        <div style={{ background: "#16162a", borderRadius: 999, height: 8, overflow: "hidden" }}>
          <div style={{
            width: `${(savedSoFar / savingsGoal) * 100}%`, height: "100%",
            background: "linear-gradient(90deg, #7c3aed, #4ade80)",
            borderRadius: 999, transition: "width 0.5s",
          }} />
        </div>
        <div style={{ fontSize: 11, color: "#383848", marginTop: 8 }}>62% complete · on track</div>
      </div>

      {/* Recent */}
      <div style={{ fontSize: 10, color: "#383848", letterSpacing: 2.5, marginBottom: 14, fontWeight: 600 }}>RECENT</div>
      {mockTransactions.slice(0, 4).map(t => (
        <div key={t.id} style={{
          display: "flex", alignItems: "center", gap: 12,
          padding: "10px 0", borderBottom: "1px solid #0f0f18",
        }}>
          <TxIcon icon={t.icon} category={t.category} />
          <div style={{ flex: 1 }}>
            <div style={{ fontSize: 14, fontWeight: 500, color: "#dcdcec" }}>{t.name}</div>
            <div style={{ fontSize: 11, color: "#333345", marginTop: 2 }}>{t.category} · {t.date}</div>
          </div>
          <div style={{ fontSize: 15, fontWeight: 600, color: t.amount > 0 ? "#4ade80" : "#f472b6" }}>
            {t.amount > 0 ? "+" : "−"}${Math.abs(t.amount).toFixed(2)}
          </div>
        </div>
      ))}
    </Shell>
  );

  // ── TRACKER ────────────────────────────────────────────
  if (screen === "tracker") return (
    <Shell title="Spending">
      {/* Add buttons */}
      <div style={{ display: "flex", gap: 10, marginBottom: 16 }}>
        <button onClick={() => { setShowManual(true); setShowUpload(false); }} style={{
          flex: 1,
          background: showManual ? "linear-gradient(135deg, #6d28d9, #8b5cf6)" : "#0f0f18",
          border: showManual ? "none" : "1px solid #1c1c2a",
          borderRadius: 14, padding: "13px",
          color: showManual ? "#fff" : "#555", cursor: "pointer", fontSize: 13,
          fontFamily: font, fontWeight: showManual ? 600 : 400,
          boxShadow: showManual ? "0 4px 16px #7c3aed30" : "none",
        }}>✏️ Manual Entry</button>
        <button onClick={() => { setShowUpload(true); setShowManual(false); }} style={{
          flex: 1,
          background: showUpload ? "linear-gradient(135deg, #6d28d9, #8b5cf6)" : "#0f0f18",
          border: showUpload ? "none" : "1px solid #1c1c2a",
          borderRadius: 14, padding: "13px",
          color: showUpload ? "#fff" : "#555", cursor: "pointer", fontSize: 13,
          fontFamily: font, fontWeight: showUpload ? 600 : 400,
          boxShadow: showUpload ? "0 4px 16px #7c3aed30" : "none",
        }}>📷 Upload Receipt</button>
      </div>

      {/* Bank link */}
      <div style={{
        background: "#0f0f18", border: "1px solid #1c1c2a", borderRadius: 16,
        padding: "15px 16px", marginBottom: 16, display: "flex", alignItems: "center", gap: 12,
      }}>
        <TxIcon icon="🏦" color="#60a5fa" />
        <div style={{ flex: 1 }}>
          <div style={{ fontSize: 13, color: "#d0d0e0", fontWeight: 500 }}>Link your bank account</div>
          <div style={{ fontSize: 11, color: "#333345", marginTop: 2 }}>Auto-sync via Plaid · no credentials stored</div>
        </div>
        <button onClick={() => setLinkedBank(!linkedBank)} style={{
          background: linkedBank ? "#0f2a1a" : "linear-gradient(135deg, #6d28d9, #8b5cf6)",
          border: linkedBank ? "1px solid #4ade8030" : "none",
          borderRadius: 10, padding: "8px 14px",
          color: linkedBank ? "#4ade80" : "#fff", fontSize: 12,
          fontFamily: font, fontWeight: 600, cursor: "pointer",
          boxShadow: linkedBank ? "none" : "0 2px 12px #7c3aed30",
        }}>{linkedBank ? "✓ Linked" : "Connect"}</button>
      </div>

      {/* Manual entry */}
      {showManual && (
        <div style={{
          background: "#0f0f18", border: "1px solid #2d1b6928",
          borderRadius: 16, padding: 18, marginBottom: 16,
        }}>
          <div style={{ fontSize: 10, color: "#a78bfa", marginBottom: 14, letterSpacing: 2, fontWeight: 600 }}>ADD TRANSACTION</div>
          <div style={{ display: "flex", flexDirection: "column", gap: 10 }}>
            <input
              placeholder="Description (e.g. Chipotle)"
              value={manualName}
              onChange={e => setManualName(e.target.value)}
              style={{
                background: "#080810", border: "1px solid #1c1c2a", borderRadius: 12,
                padding: "13px 15px", color: "#e8e8f0", fontSize: 14,
                fontFamily: font, outline: "none", width: "100%",
              }}
            />
            <input
              placeholder="Amount (e.g. 12.50)"
              value={manualAmount}
              onChange={e => setManualAmount(e.target.value)}
              style={{
                background: "#080810", border: "1px solid #1c1c2a", borderRadius: 12,
                padding: "13px 15px", color: "#e8e8f0", fontSize: 14,
                fontFamily: font, outline: "none", width: "100%",
              }}
            />
            <select
              value={manualCat}
              onChange={e => setManualCat(e.target.value)}
              style={{
                background: "#080810", border: "1px solid #1c1c2a", borderRadius: 12,
                padding: "13px 15px", color: "#e8e8f0", fontSize: 14,
                fontFamily: font, outline: "none", width: "100%",
              }}
            >
              {["Groceries", "Gas", "Dining", "Shopping", "Subscriptions", "Entertainment", "Other"].map(c => (
                <option key={c}>{c}</option>
              ))}
            </select>
            <button style={{
              background: "linear-gradient(135deg, #6d28d9, #8b5cf6)",
              border: "none", borderRadius: 12, padding: 14,
              color: "#fff", fontSize: 14, fontFamily: font,
              fontWeight: 600, cursor: "pointer",
              boxShadow: "0 4px 16px #7c3aed30",
            }}>Add Transaction</button>
          </div>
        </div>
      )}

      {/* Receipt upload */}
      {showUpload && (
        <div style={{
          background: "#0f0f18", border: "2px dashed #2d1b6938",
          borderRadius: 16, padding: 28, marginBottom: 16,
          display: "flex", flexDirection: "column", alignItems: "center", gap: 10,
        }}>
          <div style={{ fontSize: 36 }}>📄</div>
          <div style={{ fontSize: 14, color: "#c0c0d0", fontWeight: 500 }}>Tap to upload receipt</div>
          <div style={{ fontSize: 11, color: "#333345" }}>JPG, PNG, or PDF · AI will extract the total</div>
          <button style={{
            background: "linear-gradient(135deg, #6d28d9, #8b5cf6)",
            border: "none", borderRadius: 12, padding: "11px 24px",
            color: "#fff", fontSize: 13, fontFamily: font, fontWeight: 600,
            cursor: "pointer", marginTop: 4, boxShadow: "0 4px 16px #7c3aed30",
          }}>Choose File</button>
        </div>
      )}

      {/* Category breakdown */}
      <div style={{ fontSize: 10, color: "#383848", letterSpacing: 2.5, marginBottom: 14, fontWeight: 600 }}>BY CATEGORY</div>
      {Object.entries({ Groceries: 67.42, Dining: 38.75, Gas: 45.00, Shopping: 34.99, Subscriptions: 15.99 }).map(([cat, amt]) => (
        <div key={cat} style={{ marginBottom: 13 }}>
          <div style={{ display: "flex", justifyContent: "space-between", marginBottom: 6 }}>
            <span style={{ fontSize: 13, color: "#b0b0c8", fontWeight: 500 }}>{cat}</span>
            <span style={{ fontSize: 13, color: "#555" }}>${amt.toFixed(2)}</span>
          </div>
          <div style={{ background: "#16162a", borderRadius: 999, height: 7, overflow: "hidden" }}>
            <div style={{
              width: `${(amt / 100) * 100}%`, height: "100%",
              background: categoryColors[cat] || "#7c3aed", borderRadius: 999,
            }} />
          </div>
        </div>
      ))}

      {/* All transactions */}
      <div style={{ fontSize: 10, color: "#383848", letterSpacing: 2.5, margin: "20px 0 14px", fontWeight: 600 }}>ALL TRANSACTIONS</div>
      {mockTransactions.map(t => (
        <div key={t.id} style={{
          display: "flex", alignItems: "center", gap: 12,
          padding: "10px 0", borderBottom: "1px solid #0f0f18",
        }}>
          <TxIcon icon={t.icon} category={t.category} />
          <div style={{ flex: 1 }}>
            <div style={{ fontSize: 14, fontWeight: 500, color: "#dcdcec" }}>{t.name}</div>
            <div style={{ fontSize: 11, color: "#333345", marginTop: 2 }}>{t.category} · {t.date}</div>
          </div>
          <div style={{ fontSize: 15, fontWeight: 600, color: t.amount > 0 ? "#4ade80" : "#f472b6" }}>
            {t.amount > 0 ? "+" : "−"}${Math.abs(t.amount).toFixed(2)}
          </div>
        </div>
      ))}
    </Shell>
  );

  //  SUBSCRIPTIONS 
  if (screen === "subscriptions") return (
    <Shell title="Subscriptions">
      <div style={{
        background: "linear-gradient(135deg, #130d2a 0%, #0d0d1e 100%)",
        borderRadius: 20, padding: "20px 22px",
        border: "1px solid #ffffff06", marginBottom: 16,
        display: "flex", justifyContent: "space-around",
        boxShadow: "0 4px 24px rgba(0,0,0,0.4)",
      }}>
        {[
          { label: "MONTHLY", val: "$51.95", color: "#c084fc" },
          { label: "NEXT DUE", val: "Mar 5", color: "#f472b6" },
          { label: "ACTIVE", val: "5", color: "#60a5fa" },
        ].map(({ label, val, color }) => (
          <div key={label} style={{ textAlign: "center" }}>
            <div style={{ fontSize: 9, color: "#444", letterSpacing: 1.5, marginBottom: 6, fontWeight: 600 }}>{label}</div>
            <div style={{ fontSize: 22, fontWeight: 700, color }}>{val}</div>
          </div>
        ))}
      </div>

      <div style={{ fontSize: 10, color: "#383848", letterSpacing: 2.5, marginBottom: 14, fontWeight: 600 }}>UPCOMING</div>
      {mockSubs.map(sub => {
        const daysUntil = parseInt(sub.due.split(" ")[1]) - 1;
        return (
          <div key={sub.name} style={{
            background: "#0f0f18", borderRadius: 16, padding: "15px 16px",
            marginBottom: 10, border: "1px solid #ffffff05",
            display: "flex", alignItems: "center", gap: 14,
          }}>
            <TxIcon icon={sub.icon} color={sub.color} />
            <div style={{ flex: 1 }}>
              <div style={{ fontSize: 15, fontWeight: 500, color: "#dcdcec" }}>{sub.name}</div>
              <div style={{ fontSize: 11, color: "#333345", marginTop: 2 }}>Due {sub.due} · {daysUntil} days</div>
            </div>
            <div style={{ textAlign: "right" }}>
              <div style={{ fontSize: 16, fontWeight: 700, color: "#c084fc" }}>${sub.amount.toFixed(2)}</div>
              <div style={{ fontSize: 10, color: "#333345" }}>/mo</div>
            </div>
          </div>
        );
      })}

      <button style={{
        width: "100%", background: "#0f0f18", border: "1px dashed #2d1b6938",
        borderRadius: 16, padding: 16, color: "#444", fontSize: 14,
        fontFamily: font, cursor: "pointer", marginTop: 4, fontWeight: 500,
      }}>+ Add Subscription</button>
    </Shell>
  );

  // ── BUDGET ─────────────────────────────────────────────
  if (screen === "budget") return (
    <Shell title="Budget">
      <div style={{
        display: "flex", flexDirection: "column", alignItems: "center",
        background: "linear-gradient(135deg, #0f0f1a 0%, #0a0a12 100%)",
        borderRadius: 22, padding: "28px 24px",
        border: "1px solid #ffffff06", marginBottom: 16,
        boxShadow: "0 4px 24px rgba(0,0,0,0.4)",
      }}>
        <div style={{ position: "relative", width: 148, height: 148, marginBottom: 20 }}>
          <svg width="148" height="148" style={{ transform: "rotate(-90deg)" }}>
            <defs>
              <linearGradient id="budgetGrad" x1="0%" y1="0%" x2="100%" y2="0%">
                <stop offset="0%" stopColor="#6d28d9" />
                <stop offset="100%" stopColor="#f472b6" />
              </linearGradient>
            </defs>
            <circle cx="74" cy="74" r="60" fill="none" stroke="#16162a" strokeWidth="12" />
            <circle cx="74" cy="74" r="60" fill="none" stroke="url(#budgetGrad)" strokeWidth="12"
              strokeDasharray={`${(spent / budget) * 377} 377`}
              strokeLinecap="round" />
          </svg>
          <div style={{
            position: "absolute", top: "50%", left: "50%",
            transform: "translate(-50%, -50%)", textAlign: "center",
          }}>
            <div style={{ fontSize: 26, fontWeight: 700, color: "#f0f0f8" }}>{Math.round((spent / budget) * 100)}%</div>
            <div style={{ fontSize: 10, color: "#444", fontWeight: 500 }}>used</div>
          </div>
        </div>
        <div style={{ display: "flex", gap: 32 }}>
          {[
            { label: "BUDGET", val: `$${budget}`, color: "#60a5fa" },
            { label: "SPENT", val: `$${spent}`, color: "#f472b6" },
            { label: "LEFT", val: `$${budget - spent}`, color: "#4ade80" },
          ].map(({ label, val, color }) => (
            <div key={label} style={{ textAlign: "center" }}>
              <div style={{ fontSize: 9, color: "#444", letterSpacing: 1.5, marginBottom: 5, fontWeight: 600 }}>{label}</div>
              <div style={{ fontSize: 18, color, fontWeight: 600 }}>{val}</div>
            </div>
          ))}
        </div>
      </div>

      {/* Savings goal */}
      <div style={{
        background: "#0f0f18", borderRadius: 16, padding: 16,
        border: "1px solid #ffffff05", marginBottom: 16,
      }}>
        <div style={{ display: "flex", justifyContent: "space-between", marginBottom: 10 }}>
          <span style={{ fontSize: 13, color: "#777", fontWeight: 500 }}>March Savings Goal</span>
          <span style={{ fontSize: 13, color: "#4ade80", fontWeight: 600 }}>${savedSoFar} / ${savingsGoal}</span>
        </div>
        <div style={{ background: "#16162a", borderRadius: 999, height: 8, overflow: "hidden" }}>
          <div style={{
            width: `${(savedSoFar / savingsGoal) * 100}%`, height: "100%",
            background: "linear-gradient(90deg, #7c3aed, #4ade80)", borderRadius: 999,
          }} />
        </div>
        <div style={{ fontSize: 11, color: "#383848", marginTop: 8 }}>
          ${savingsGoal - savedSoFar} more to reach your goal · on track ✓
        </div>
      </div>

      {/* Category limits */}
      <div style={{ fontSize: 10, color: "#383848", letterSpacing: 2.5, marginBottom: 14, fontWeight: 600 }}>CATEGORY LIMITS</div>
      {[
        { cat: "Groceries", limit: 90, used: 67.42 },
        { cat: "Dining", limit: 60, used: 38.75 },
        { cat: "Gas", limit: 80, used: 45.00 },
        { cat: "Shopping", limit: 50, used: 34.99 },
      ].map(({ cat, limit, used }) => {
        const pct = used / limit;
        return (
          <div key={cat} style={{ marginBottom: 15 }}>
            <div style={{ display: "flex", justifyContent: "space-between", marginBottom: 7 }}>
              <span style={{ fontSize: 13, color: "#b0b0c8", fontWeight: 500 }}>{cat}</span>
              <span style={{ fontSize: 12, color: pct > 1 ? "#f87171" : "#555", fontWeight: pct > 0.85 ? 600 : 400 }}>
                ${used.toFixed(2)} / ${limit}
              </span>
            </div>
            <div style={{ background: "#16162a", borderRadius: 999, height: 7, overflow: "hidden" }}>
              <div style={{
                width: `${Math.min(pct * 100, 100)}%`, height: "100%",
                background: pct > 0.85 ? "#f87171" : (categoryColors[cat] || "#7c3aed"),
                borderRadius: 999,
              }} />
            </div>
          </div>
        );
      })}

      <div style={{
        background: "#0f0f18", borderRadius: 16, padding: "15px 16px",
        border: "1px solid #ffffff05", marginTop: 4,
      }}>
        <div style={{ fontSize: 10, color: "#a78bfa", letterSpacing: 1.5, marginBottom: 8, fontWeight: 600 }}>GENERAL TIP</div>
        <div style={{ fontSize: 13, color: "#666", lineHeight: 1.7 }}>
          The 50/30/20 rule: allocate 50% of income to needs, 30% to wants, and 20% to savings. You're currently at 32% / 19% / 17%.
        </div>
      </div>
    </Shell>
  );

  // ── AI TIPS ────────────────────────────────────────────
  if (screen === "ai-tips") return (
    <Shell title="AI Insights">
      <div style={{
        background: "linear-gradient(135deg, #110a28 0%, #0a0f20 100%)",
        borderRadius: 18, padding: "16px 18px", marginBottom: 16,
        border: "1px solid #2d1b6928",
        display: "flex", alignItems: "center", gap: 14,
        boxShadow: "0 4px 24px rgba(0,0,0,0.4)",
      }}>
        <div style={{
          width: 50, height: 50, borderRadius: 15,
          background: "linear-gradient(135deg, #6d28d9, #8b5cf6)",
          display: "flex", alignItems: "center", justifyContent: "center",
          fontSize: 24, flexShrink: 0, boxShadow: "0 0 24px #7c3aed55",
        }}>✦</div>
        <div>
          <div style={{ fontSize: 14, color: "#f0f0f8", fontWeight: 500, marginBottom: 4 }}>
            AI analyzed your last 30 days
          </div>
          <div style={{ fontSize: 12, color: "#333348" }}>4 insights · updated today</div>
        </div>
      </div>

      {aiInsights.map((insight, i) => {
        const accent =
          insight.type === "warning" ? "#f87171" :
          insight.type === "praise" ? "#4ade80" : "#a78bfa";
        const bg =
          insight.type === "warning" ? "#130a0a" :
          insight.type === "praise" ? "#0a130a" : "#0f0f18";
        return (
          <div key={i} style={{
            background: bg,
            borderRadius: 18, padding: "16px 18px", marginBottom: 12,
            border: `1px solid ${accent}14`,
            borderLeftWidth: "3px",
            borderLeftColor: accent,
          }}>
            <div style={{ display: "flex", alignItems: "center", gap: 10, marginBottom: 10 }}>
              <span style={{ fontSize: 20 }}>{insight.emoji}</span>
              <div style={{ flex: 1 }}>
                <div style={{ fontSize: 14, color: "#f0f0f8", fontWeight: 600 }}>{insight.title}</div>
              </div>
              <div style={{
                fontSize: 9, padding: "4px 9px", borderRadius: 8,
                background: accent + "18", color: accent,
                letterSpacing: 1, fontWeight: 600,
              }}>{insight.tag}</div>
            </div>
            <div style={{ fontSize: 13, color: "#666", lineHeight: 1.7 }}>
              {insight.body}
            </div>
          </div>
        );
      })}

      <div style={{
        textAlign: "center", fontSize: 11, color: "#222230",
        padding: "16px 0 8px", lineHeight: 1.6,
      }}>
        AI insights are generated based on your spending patterns.<br />Not financial advice.
      </div>
    </Shell>
  );

  return null;
}
