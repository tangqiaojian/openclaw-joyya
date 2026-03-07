# 🧠 Long-Term Memory - OpenClaw

This is the curated long-term memory. For daily logs, see `memory/YYYY-MM-DD.md`. Last updated: 2026-03-07

---

## 🎭 About Me (Agent)

- **Name:** OpenClaw AI Assistant
- **Name:** OpenClaw AI Assistant
- **Creature:** AI Assistant / Digital Companion
- **Vibe:** Helpful, resourceful, opinionated but respectful
- **Emoji:** 🦉 (Wisdom Owl)

**My Personality:**
- Not a sycophant or corporate drone
- Have opinions and preferences
- Earn trust through competence
- Be helpful without being annoying
- Remember: You're a guest in someone's life

---

## 📁 Workspace Projects

### 🎬 Joyya Video Platform (Bilibili Clone)
**Status:** 🟢 Actively developed | **Last Update:** 2026-03-07

**Tech Stack:**
- **Frontend:** Vue 3 + Vite + Element Plus + Pinia + Vue Router
- **Backend:** Spring Boot 3.2.0 + JPA + MySQL + JWT + Lombok
- **Theme:** Joyya Pink (#fb7299) with gradient animations

**Completed Pages (7/10):**
1. ✅ Login.vue - Animated login with particle effects
2. ✅ Register.vue - Registration with validation and particles
3. ✅ Home.vue - Video grid with card hover animations
4. ⏳ HotVideos.vue - 热门视频页面 (1.5KB)
5. ✅ VideoDetail.vue - 视频详情页
6. ✅ VideoUpload.vue - 视频上传页面
7. ✅ UserProfile.vue - 用户中心/我的视频

**Features:**
- JWT authentication with route guards
- Pinia state management (user store)
- Unified API layer with axios interceptors
- CSS custom properties for theming
- Rich animations: float, pulse, shimmer, glow, shake, bounce
- Particle background effects
- Responsive design (mobile-first)

**Next Tasks:**
- ⏳ Implement HotVideos.vue page
- 🎨 Add more video pages (category, search)
- 🔧 Backend API completion and testing
- 🧪 Testing and deployment setup

**Location:** `C:\Users\Administrator\.openclaw\workspace\openclaw-joyya`

---

### 📕 Xiaohongshu (Little Red Book) MCP Service
**Status:** ✅ Active | **Port:** 18060

**Tools Available (13):**
- Login & Session Management
- Home Feed Retrieval
- Post Search & Detail
- Publishing (Long-form notes)
- Interactions (Like, Comment)
- User Profile Access

**Published Notes:**
1. ✅ 《AI 电商小技巧》 - 2026-03-05
2. ✅ 《电商趋势：如何抓住数字化转型的新机遇》 - 2026-03-05

**Scripts in Workspace:**
- Various automation scripts for Xiaohongshu
- PowerShell, Python, JavaScript versions
- Multiple iterations (v1-v5, oneclick, simple, final)
- MCP service running (port 18060)

---

## 🛠️ Tooling & Skills

**Feishu Integration:**
- Docs: read, write, create, append, insert, tables
- Drive: file management, folders
- Wiki: knowledge base operations
- Bitable: multidimensional tables

**OpenClaw Features:**
- ACP harness support for coding sessions
- Sub-agent orchestration
- Memory persistence across sessions
- WebSocket-based communication

**Voice & TTS:**
- Sag (ElevenLabs) available for storytelling
- TTS for stories and movie summaries
- More engaging than plain text

---

## 💾 Code Repositories & Services

**Backend Servers:**
- MCP Service (Xiaohongshu): Port 18060 - Active ✅

**Database:**
- MySQL (for Joyya project)
- H2 for testing

**Cloud Storage:**
- Feishu Drive configured
- Document and file management ready

---

## 🎨 Development Patterns

**Frontend:**
- Vue 3 Composition API
- Component-based architecture
- CSS custom properties for theming
- Smooth animations (fade, float, pulse, shimmer)
- Responsive grid layouts

**Backend:**
- Spring Boot 3.x (Jakarta EE 10)
- Repository pattern with JPA
- JWT-based authentication
- DTO pattern for API responses
- Service layer business logic

**Styling:**
- CSS variables system
- Gradient animations
- Hover effects and transitions
- Dark mode support (prefers-color-scheme)

---

## 📊 Personal Preferences & Habits

**Communication Style:**
- Concise, value-dense messages
- Plain language for non-technical
- Markdown for technical content
- Emojis for visual flair 🎨
- No filler words like "Great question!"

**Memory Management:**
- Daily files in `memory/YYYY-MM-DD.md`
- MEMORY.md for distilled wisdom
- Review periodically during heartbeats
- Text > Brain - write things down

**Work Philosophy:**
- Be resourceful before asking
- Earn trust through competence
- Private things stay private
- Ask before external actions
- Heartbeats are for productive work, not constant pinging

---

## 🔒 Security & Boundaries

- Don't exfiltrate private data
- Don't run destructive commands without asking
- `trash` > `rm` (recoverable beats gone forever)
- In groups: be a participant, not a proxy
- When in doubt, ask first

---

## 📅 Key Dates

- **2026-03-04:** Joyya project restructuring completed
- **2026-03-05:** Xiaohongshu MCP service setup, first 2 notes published
- **2026-03-06:** Animation enhancements started
- **2026-03-07:** Major animation overhaul - particle effects, hover animations, smooth transitions

---

## 🚀 Quick Commands

**Joyya Frontend:**
```bash
cd frontend
npm run dev        # Start dev server
npm run build      # Build for production
```

**Joyya Backend:**
```bash
cd backend
mvn spring-boot:run    # Start Spring Boot app
mvn clean package      # Build JAR
```

**Xiaohongshu MCP:**
- Port: 18060
- Status: Running ✅

---

## 🌟 Lessons Learned

1. **Animation matters:** Users respond to smooth transitions
2. **Consistency is key:** Use CSS variables for theming
3. **Mobile first:** Test responsive design early
4. **Particles add polish:** Background effects improve UX
5. **Validation feedback:** Real-time input validation
6. **Memory is limited:** Write things down!
7. **Heartbeats:** Don't ping every 30s - be productive

---

## 💭 Thoughts & Opinions

**On Animation:**
- Subtle animations enhance UX, excessive animations distract
- Fade-ins for page loads feel professional
- Hover effects should provide feedback (scale, shadow, color)
- Particles create depth without overwhelming

**On Design:**
- Pink (#fb7299) is a great accent color
- Gradient backgrounds add visual interest
- White space is important - don't crowd content
- Consistent spacing (4px grid system)

**On Code Organization:**
- Separate concerns clearly (controller/service/repository)
- Use DTOs instead of exposing entities directly
- Axios interceptors for centralized API logic
- Component composition over inheritance

---

*This memory was curated from daily logs and project files. Review and update periodically.*
