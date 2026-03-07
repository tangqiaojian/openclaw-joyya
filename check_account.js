const http = require('http');

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

console.log('检查小红书账号登录状态...\n');

// Initialize
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

let step = 0;

const req1 = http.request(opt, (res) => {
    let body = '';
    res.on('data', chunk => body += chunk);
    res.on('end', () => {
        console.log('✓ Step 1: 初始化完成');
        step = 1;
        proceed();
    });
});

function proceed() {
    if (step === 1) {
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
                console.log('✓ Step 2: 通知发送完成');
                step = 2;
                proceed();
            });
        });
        
        req2.on('error', err => console.log('错误:', err.message));
        req2.write(nB);
        step++;
        return;
    }
    
    if (step === 2) {
        console.log('\nStep 3: 检查登录状态...');
        const sB = JSON.stringify({
            jsonrpc: '2.0',
            method: 'tools/call',
            params: {
                name: 'check_login_status',
                arguments: {}
            },
            id: 999
        });
        
        const req3 = http.request(opt, (res3) => {
            let b3 = '';
            res3.on('data', chunk => b3 += chunk);
            res3.on('end', () => {
                console.log('\n=== 登录状态 ===');
                console.log(b3);
            });
        });
        
        req3.on('error', err => console.log('错误:', err.message));
        req3.write(sB);
        step++;
        return;
    }
    
    if (step === 3) {
        console.log('\nStep 4: 获取用户信息...');
        const pB = JSON.stringify({
            jsonrpc: '2.0',
            method: 'tools/call',
            params: {
                name: 'user_profile',
                arguments: {}
            },
            id: 1000
        });
        
        const req4 = http.request(opt, (res4) => {
            let b4 = '';
            res4.on('data', chunk => b4 += chunk);
            res4.on('end', () => {
                console.log('\n=== 用户信息 ===');
                console.log(b4);
            });
        });
        
        req4.on('error', err => console.log('错误:', err.message));
        req4.write(pB);
    }
}

req1.on('error', err => {
    console.log('错误:', err.message);
});

req1.write(initB);
req1.end();
