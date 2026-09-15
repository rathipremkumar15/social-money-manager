"use client";

import { useState } from "react";

const API = process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080";
type Opportunity = { title: string; category: string; description: string; difficulty: number; startupCostInr: number; scalability: number; score: number };
type PrivacyResult = { safeToPublish: boolean; findings: { type: string; severity: string; message: string }[] };

export default function Home() {
  const [query, setQuery] = useState("");
  const [opportunities, setOpportunities] = useState<Opportunity[]>([]);
  const [topic, setTopic] = useState("AI content creation");
  const [ideas, setIdeas] = useState<string[]>([]);
  const [text, setText] = useState("");
  const [privacy, setPrivacy] = useState<PrivacyResult | null>(null);
  const [loading, setLoading] = useState(false);

  async function findOpportunities() { setLoading(true); try { const r = await fetch(`${API}/api/v1/opportunities?q=${encodeURIComponent(query)}`); setOpportunities(await r.json()); } finally { setLoading(false); } }
  async function generateIdeas() { const r = await fetch(`${API}/api/v1/content/ideas`, { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify({ topic }) }); setIdeas((await r.json()).ideas); }
  async function privacyCheck() { const r = await fetch(`${API}/api/v1/privacy/check`, { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify({ text }) }); setPrivacy(await r.json()); }

  return <main style={{ maxWidth: 1100, margin: "0 auto", padding: 32, fontFamily: "system-ui" }}>
    <header><p>SM MANAGER · MVP</p><h1>Social Money Manager</h1><p>Research opportunities, create content ideas, and protect personal information before publishing.</p></header>
    <section style={{ marginTop: 32 }}><h2>💰 Opportunity Finder</h2><div style={{ display: "flex", gap: 8 }}><input value={query} onChange={e => setQuery(e.target.value)} placeholder="e.g. AI, service, digital product" style={{ flex: 1, padding: 12 }} /><button onClick={findOpportunities} disabled={loading}>{loading ? "Searching…" : "Find ideas"}</button></div><div style={{ display: "grid", gap: 12, marginTop: 16 }}>{opportunities.map(o => <article key={o.title} style={{ border: "1px solid #ddd", borderRadius: 10, padding: 16 }}><strong>{o.title}</strong><p>{o.description}</p><small>{o.category} · Difficulty {o.difficulty}/5 · Starting cost ₹{o.startupCostInr.toLocaleString("en-IN")} · Score {o.score}/100</small></article>)}</div></section>
    <section style={{ marginTop: 40 }}><h2>📝 Content Ideas</h2><div style={{ display: "flex", gap: 8 }}><input value={topic} onChange={e => setTopic(e.target.value)} style={{ flex: 1, padding: 12 }} /><button onClick={generateIdeas}>Generate</button></div><ul>{ideas.map(i => <li key={i} style={{ marginTop: 8 }}>{i}</li>)}</ul></section>
    <section style={{ marginTop: 40 }}><h2>🛡️ Privacy Guard</h2><textarea value={text} onChange={e => setText(e.target.value)} placeholder="Paste a post/caption here to scan for email, phone numbers, or credential markers…" rows={5} style={{ width: "100%", padding: 12 }} /><button onClick={privacyCheck} style={{ marginTop: 8 }}>Check before publishing</button>{privacy && <div style={{ marginTop: 12, padding: 16, border: "1px solid #ddd", borderRadius: 10 }}><strong>{privacy.safeToPublish ? "✅ No detected privacy risks" : "⚠️ Review before publishing"}</strong>{privacy.findings.map(f => <p key={f.type}>{f.severity}: {f.message}</p>)}</div>}</section>
  </main>;
}
