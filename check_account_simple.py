#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json
import urllib.request

def request(method, params):
    """Send MCP request"""
    data = json.dumps({
        'jsonrpc': '2.0',
        'method': method,
        'params': params,
        'id': 999
    }, ensure_ascii=False).encode('utf-8')
    
    req = urllib.request.Request(
        'http://localhost:18060/mcp',
        data=data,
        headers={
            'Content-Type': 'application/json',
            'Accept': 'application/json, text/event-stream'
        }
    )
    
    try:
        resp = urllib.request.urlopen(req, timeout=30)
        return json.loads(resp.read().decode('utf-8'))
    except Exception as e:
        return {'error': str(e)}

if __name__ == '__main__':
    print('=== 检查小红书账号状态 ===\n')
    
    print('Step 1: 初始化会话...')
    result = request('initialize', {
        'protocolVersion': '2024-11-05',
        'capabilities': {},
        'clientInfo': {'name': 'test', 'version': '1.0.0'}
    })
    print(f'  ✓ 初始化成功')
    
    print('\nStep 2: 发送初始化通知...')
    result = request('notifications/initialized', {})
    print('  ✓ 通知发送成功')
    
    print('\nStep 3: 检查登录状态...')
    result = request('tools/call', {
        'name': 'check_login_status',
        'arguments': {}
    })
    print('\n=== 登录状态 ===')
    print(json.dumps(result, ensure_ascii=False, indent=2))
    
    print('\nStep 4: 获取用户信息...')
    result = request('tools/call', {
        'name': 'user_profile',
        'arguments': {}
    })
    print('\n=== 用户信息 ===')
    print(json.dumps(result, ensure_ascii=False, indent=2))
