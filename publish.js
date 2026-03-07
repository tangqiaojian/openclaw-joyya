const http = require('http');

console.log('=== 小红书笔记一键发布 ===\n');

// 用户配置
const title = '电商趋势：数字化转型机遇';
const content = '在数字经济蓬勃发展的今天，电商行业正经历着前所未有的变革。AI、大数据、物联网重塑零售生态。AI 推荐提升转化率 30%，直播电商创造沉浸式体验，社交电商熟人推荐转化率高，新零售店仓一体 30 分钟达，跨境电商破 2 万亿美元。持续创新是关键。';
const imgUrl = 'https://pic.rmb.bdstatic.com/bjh/cms/250516/71df94adbc9f3dfa26a2267c1829d962_1747385010.5094_693.jpeg';
const tags = ['电商', 'AI 电商', '新零售'];

console.log('笔记内容:');
console.log('  标题:', title);
console.log('  字数:', content.length);
console.log('  图片:', imgUrl);
console.log('  标签:', tags.join(', '));
console.log('');

const options = {
    hostname: 'localhost',
    port: 18060,
    path: '/mcp',
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json, text/event-stream'
    }
};

// Step 1: Initialize session
console.log('Step 1: 初始化 MCP 会话...');
const initBody = JSON.stringify({
    jsonrpc: '2.0',
    method: 'initialize',
    params: {
        protocolVersion: '2024-11-05',
        capabilities: {},
        clientInfo: { name: 'xiaohongshu_publisher', version: '1.0.0' }
    },
    id: 1
});

const initReq = http.request(options, (res) => {
    let body = '';
    res.on('data', chunk => body += chunk);
    res.on('end', () => {
        console.log('  ✓ 会话初始化成功');
        
        // Step 2: Send notification
        console.log('Step 2: 发送初始化通知...');
        const notifBody = JSON.stringify({
            jsonrpc: '2.0',
            method: 'notifications/initialized',
            params: {},
            id: null
        });
        
        const notifReq = http.request(options, (res2) => {
            let b = '';
            res2.on('data', chunk => b += chunk);
            res2.on('end', () => {
                console.log('  ✓ 通知发送成功');
                
                // Step 3: Publish note
                console.log('Step 3: 发布小红书笔记...');
                const publishBody = JSON.stringify({
                    jsonrpc: '2.0',
                    method: 'tools/call',
                    params: {
                        name: 'publish_content',
                        arguments: {
                            title: title,
                            content: content,
                            images: [imgUrl],
                            tags: tags,
                            is_original: false
                        }
                    },
                    id: 2
                });
                
                const pubReq = http.request(options, (res3) => {
                    let b2 = '';
                    res3.on('data', chunk => b2 += chunk);
                    res3.on('end', () => {
                        const result = JSON.parse(b2);
                        console.log('');
                        console.log('===== 发布成功 =====');
                        console.log('');
                        console.log('发布详情:');
                        console.log(JSON.stringify(result.result, null, 2));
                    });
                    pubReq.write(publishBody);
                    pubReq.end();
                });
                
                notifReq.on('error', (e) => {
                    console.log('  ✗ 通知发送失败:', e.message);
                    process.exit(1);
                });
                
                notifReq.write(notifBody);
                notifReq.end();
            });
            
            notifReq.on('error', (e) => {
                console.log('  ✗ 请求错误:', e.message);
            });
        });
        
        initReq.on('error', (e) => {
            console.log('  ✗ 初始化失败:', e.message);
            process.exit(1);
        });
        
        initReq.write(initBody);
        initReq.end();
    });
    
    initReq.on('error', (e) => {
        console.log('  ✗ 请求错误:', e.message);
    });
});

initReq.write(initBody);
initReq.end();
