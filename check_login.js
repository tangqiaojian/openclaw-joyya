const http = require('http');

console.log('=== 检查 MCP 登录状态 ===\n');

const opt = {
    hostname: 'localhost',
    port: 18060,
    path: '/mcp',
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json, text/event-stream'
    }
};

// Step 1: Initialize
console.log('Step 1: 初始化会话...');
const initB = JSON.stringify({
    jsonrpc: '2.0',
    method: 'initialize',
    params: {
        protocolVersion: '2024-11-05',
        capabilities: {},
        clientInfo: { name: 'test', version: '1.0.0' }
    },
    id: 1
});

let initComplete = false;

const req1 = http.request(opt, (res) => {
    let body = '';
    res.on('data', chunk => body += chunk);
    res.on('end', () => {
        console.log('  ✓ 初始化成功');
        initComplete = true;
        
        // Step 2: Notification
        console.log('\nStep 2: 发送通知...');
        const nB = JSON.stringify({
            jsonrpc: '2.0',
            method: 'notifications/initialized',
            params: {},
            id: null
        });
        
        const req2 = http.request(opt, (res2) => {
            let b2 = '';
            res2.on('data', chunk => b2 += chunk);
            res2.on('end', () => {
                console.log('  ✓ 通知发送成功');
                
                // Step 3: Check login status
                console.log('\nStep 3: 检查登录状态...');
                const loginB = JSON.stringify({
                    jsonrpc: '2.0',
                    method: 'tools/call',
                    params: {
                        name: 'check_login_status',
                        arguments: {}
                    },
                    id: 100
                });
                
                const req3 = http.request(opt, (res3) => {
                    let b3 = '';
                    res3.on('data', chunk => b3 += chunk);
                    res3.on('end', () => {
                        const r = JSON.parse(b3);
                        console.log('\n=== 登录状态 ===');
                        console.log(JSON.stringify(r, null, 2));
                        
                        // Step 4: Get user profile
                        if (r.result || r.resultObj) {
                            console.log('\nStep 4: 获取用户信息...');
                            const profileB = JSON.stringify({
                                jsonrpc: '2.0',
                                method: 'tools/call',
                                params: {
                                    name: 'user_profile',
                                    arguments: {}
                                },
                                id: 101
                            });
                            
                            const req4 = http.request(opt, (res4) => {
                                let b4 = '';
                                res4.on('data', chunk => b4 += chunk);
                                res4.on('end', () => {
                                    const p = JSON.parse(b4);
                                    console.log('\n=== 用户信息 ===');
                                    console.log(JSON.stringify(p, null, 2));
                                });
                                req4.write(profileB);
                                req4.end();
                            });
                            
                            req4.on('error', (e) => {
                                console.log('  ✗ 请求错误:', e.message);
                            });
                        }
                    });
                    req3.write(loginB);
                    req3.end();
                });
                
                req3.on('error', (e) => {
                    console.log('  ✗ 请求 3 错误:', e.message);
                });
            });
            
            req2.on('error', (e) => {
                console.log('  ✗ 请求 2 错误:', e.message);
            });
        });
        
        req2.on('error', (e) => {
            console.log('  ✗ 请求 2 错误:', e.message);
        });
        
        req2.write(nB);
        req2.end();
    });
    
    req1.on('error', (e) => {
        console.log('  ✗ 初始化错误:', e.message);
    });
});

req1.write(initB);
req1.end();

req1.on('error', (e) => {
    console.log('请求错误:', e.message);
    process.exit(1);
});
