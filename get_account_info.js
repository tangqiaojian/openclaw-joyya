const http = require('http');

console.log('=== 获取小红书账号信息 ===\n');

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

console.log('Step 1: 初始化 MCP 会话...');
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

let stage = 0;
let initResult = '';

const req1 = http.request(opt, (res) => {
    let b = '';
    res.on('data', (chunk) => b += chunk);
    res.on('end', () => {
        initResult = b;
        console.log('  ✓ 初始化成功');
        stage = 1;
        runNext();
    });
});

function runNext() {
    if (stage === 1) {
        console.log('\nStep 2: 发送初始化通知...');
        const nB = JSON.stringify({
            jsonrpc: '2.0',
            method: 'notifications/initialized',
            params: {},
            id: null
        });
        
        const req2 = http.request(opt, (res2) => {
            let b2 = '';
            res2.on('data', (chunk) => b2 += chunk);
            res2.on('end', () => {
                console.log('  ✓ 通知发送成功');
                stage = 2;
                runNext();
            });
        });
        
        req2.write(nB);
        stage++;
        return;
    }
    
    if (stage === 2) {
        console.log('\nStep 3: 获取用户信息...');
        const upB = JSON.stringify({
            jsonrpc: '2.0',
            method: 'tools/call',
            params: {
                name: 'user_profile',
                arguments: {}
            },
            id: 100
        });
        
        const req3 = http.request(opt, (res3) => {
            let b3 = '';
            res3.on('data', (chunk) => b3 += chunk);
            res3.on('end', () => {
                try {
                    const r = JSON.parse(b3);
                    console.log('\n=== 账号信息 ===');
                    console.log(JSON.stringify(r, null, 2));
                } catch (e) {
                    console.log('解析错误:', e.message);
                    console.log('原始响应:', b3);
                }
            });
        });
        
        req3.on('error', (e) => {
            console.log('  ✗ 请求错误:', e.message);
        });
        
        req3.write(upB);
        stage++;
        return;
    }
    
    if (stage === 3) {
        console.log('\nStep 4: 检查登录状态...');
        const loginB = JSON.stringify({
            jsonrpc: '2.0',
            method: 'tools/call',
            params: {
                name: 'check_login_status',
                arguments: {}
            },
            id: 101
        });
        
        const req4 = http.request(opt, (res4) => {
            let b4 = '';
            res4.on('data', (chunk) => b4 += chunk);
            res4.on('end', () => {
                try {
                    const r = JSON.parse(b4);
                    console.log('\n=== 登录状态 ===');
                    console.log(JSON.stringify(r, null, 2));
                } catch (e) {
                    console.log('解析错误:', e.message);
                    console.log('原始响应:', b4);
                }
            });
        });
        
        req4.on('error', (e) => {
            console.log('  ✗ 请求错误:', e.message);
        });
        
        req4.write(loginB);
        stage++;
        return;
    }
}

req1.on('error', (e) => {
    console.log('  ✗ 初始化错误:', e.message);
});

req1.write(initB);
req1.end();
