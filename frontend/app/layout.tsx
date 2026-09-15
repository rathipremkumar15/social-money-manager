import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Social Money Manager",
  description: "AI-powered social media and online opportunity management.",
};

export default function RootLayout({ children }: Readonly<{ children: React.ReactNode }>) {
  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  );
}
